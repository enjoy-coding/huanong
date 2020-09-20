package com.feather.patrol.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.DataScope;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.common.utils.http.HttpUtils;
import com.feather.common.utils.http.HttpUtils.InputStreamCallback;
import com.feather.framework.util.ShiroUtils;
import com.feather.patrol.domain.CoordinateConvertResult;
import com.feather.patrol.domain.PtrCard;
import com.feather.patrol.domain.PtrFacade;
import com.feather.patrol.domain.PtrIssue;
import com.feather.patrol.domain.PtrLog;
import com.feather.patrol.mapper.PtrLogMapper;
import com.feather.patrol.service.IPtrCardService;
import com.feather.patrol.service.IPtrLogService;
import com.feather.prd.domain.PrdAttachment.AttachmentInfo;
import com.feather.prd.service.IPrdAttachmentService;
import com.feather.system.service.ISysConfigService;

/**
 * 巡检日志Service业务层处理
 */
@Service
public class PtrLogServiceImpl implements IPtrLogService {
    @Autowired
    private PtrLogMapper ptrLogMapper;

    @Autowired
    private IPtrCardService ptrCardService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IPrdAttachmentService prdAttachmentService;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询巡检日志
     * 
     * @param logId
     *            巡检日志ID
     * @return 巡检日志
     */
    @Override
    public PtrLog selectPtrLogById(Long logId) {
        return ptrLogMapper.selectPtrLogById(logId);
    }

    /**
     * 查询巡检日志列表
     * 
     * @param ptrLog
     *            巡检日志
     * @return 巡检日志
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<PtrLog> selectPtrLogList(PtrLog ptrLog) {
        return ptrLogMapper.selectPtrLogList(ptrLog);
    }

    /**
     * 查询最近日志外观照片
     */
    @Override
    public List<String> selectTopFacadeUrls() {
        return ptrLogMapper.selectTopFacadeUrls();
    }

    /**
     * 新增巡检日志
     * 
     * @param ptrLog
     *            巡检日志
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPtrLog(String qrcode, String coordinate, String altitude, String address, String issue,
            Long[] standards, MultipartFile[] facades) {
        List<PtrCard> cardList = ptrCardService.selectPtrCardByCode(qrcode, true);
        if (cardList == null || cardList.size() == 0) {
            throw new RuntimeException("系统未找到对应的二维码");
        }
        if (cardList.size() > 1) {
            throw new RuntimeException("二维码重复，请联系管理员处理。");
        }
        PtrCard ptrCard = cardList.get(0);
        boolean blankCardCoordinate = StringUtils.isBlank(ptrCard.getCardCoordinate());
        boolean blankCardFacade = ptrCard.getFacades() == null || ptrCard.getFacades().size() == 0;

        String loginName = ShiroUtils.getLoginName();
        Date time = new Date();

        PtrLog ptrLog = new PtrLog();
        ptrLog.setLogId(uidWorker.getNextId());
        ptrLog.setLogCard(ptrCard.getCardId());
        ptrLog.setLogQrcode(qrcode);
        ptrLog.setLogCoordinate(coordinate);
        ptrLog.setLogAltitude(altitude);
        ptrLog.setLogAddress(address);
        ptrLog.setRemark(issue);
        ptrLog.setCreateBy(loginName);
        ptrLog.setCreateTime(time);
        ptrLog.setUpdateBy(loginName);
        ptrLog.setUpdateTime(time);

        String gdApiKey = configService.selectConfigByKey("patrol_gdmap_key");
        String convertUrl = "https://restapi.amap.com/v3/assistant/coordinate/convert?key=" + gdApiKey + "&locations="
                + coordinate + "&coordsys=gps";
        String json = HttpUtils.sendGet(convertUrl);
        CoordinateConvertResult convertResult = JSONObject.parse(json, CoordinateConvertResult.class);
        if (convertResult != null && convertResult.getLocations() != null) {
            String gdLocations = convertResult.getLocations();
            ptrLog.setLogCoordinateGd(gdLocations);

            String mapUrl = "http://restapi.amap.com/v3/staticmap?key=" + gdApiKey + "&location=" + gdLocations
                    + "&zoom=16&size=900*900&&markers=mid,,A:" + gdLocations;
            byte[] bytes = (byte[]) HttpUtils.send(mapUrl, null, new InputStreamCallback() {
                @Override
                public Object handle(InputStream is) throws IOException {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        byte[] bytes = new byte[1024];
                        int length = 0;
                        while ((length = is.read(bytes)) > 0) {
                            baos.write(bytes, 0, length);
                        }
                        return baos.toByteArray();
                    }
                }
            }, false);
            try {
                String logMap = FileUploadUtils.upload(bytes, "jpg", null);
                ptrLog.setLogMap(logMap);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (blankCardCoordinate) {
            ptrCard.setCardCoordinate(coordinate);
            ptrCard.setCardCoordinateGd(ptrLog.getLogCoordinateGd());
            ptrCard.setCardAddress(ptrLog.getLogAddress());
            ptrCardService.updatePtrCard(ptrCard);
        } else {
            GlobalCoordinates gpsFrom = new GlobalCoordinates(ptrCard.getLatitude(), ptrCard.getLongitude());
            GlobalCoordinates gpsTo = new GlobalCoordinates(ptrLog.getLatitude(), ptrLog.getLongitude());
            GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, gpsFrom, gpsTo);
            int distance = (int) geoCurve.getEllipsoidalDistance();
            ptrLog.setLogOffset(distance);
        }

        int result = ptrLogMapper.insertPtrLog(ptrLog);

        if (standards != null) {
            for (Long stdd : standards) {
                PtrIssue ptrIssue = new PtrIssue();
                ptrIssue.setIssueId(uidWorker.getNextId());
                ptrIssue.setIssueLog(ptrLog.getLogId());
                ptrIssue.setIssueStandard(stdd);
                ptrLogMapper.insertLogIssue(ptrIssue);
            }
        }

        if (facades != null) {
            for (MultipartFile file : facades) {
                AttachmentInfo info = prdAttachmentService.upload(file, false, false);
                PtrFacade ptrFacade = new PtrFacade();
                ptrFacade.setFacadeId(uidWorker.getNextId());
                ptrFacade.setEntityId(ptrLog.getLogId());
                ptrFacade.setFacadeUrl(info.getFilePath());
                ptrLogMapper.insertLogFacade(ptrFacade);
            }
        }
        if (blankCardFacade) {
            ptrCardService.insertFacadeFromLog(ptrCard.getCardId(), ptrLog.getLogId());
        }

        return result;
    }

    /**
     * 修改巡检日志
     * 
     * @param ptrLog
     *            巡检日志
     * @return 结果
     */
    @Override
    public int updatePtrLog(PtrLog ptrLog) {
        return ptrLogMapper.updatePtrLog(ptrLog);
    }

    /**
     * 删除巡检日志对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePtrLogByIds(String ids) {
        return ptrLogMapper.deletePtrLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检日志信息
     * 
     * @param logId
     *            巡检日志ID
     * @return 结果
     */
    @Override
    public int deletePtrLogById(Long logId) {
        return ptrLogMapper.deletePtrLogById(logId);
    }
}
