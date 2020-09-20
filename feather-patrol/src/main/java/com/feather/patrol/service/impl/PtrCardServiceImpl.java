package com.feather.patrol.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.DataScope;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.framework.util.ShiroUtils;
import com.feather.patrol.domain.PtrCard;
import com.feather.patrol.mapper.PtrCardMapper;
import com.feather.patrol.service.IPtrCardService;
import com.feather.system.domain.SysDictData;
import com.feather.system.domain.SysUser;
import com.feather.system.mapper.SysDictDataMapper;

/**
 * 巡点Service业务层处理
 */
@Service
public class PtrCardServiceImpl implements IPtrCardService {
    @Autowired
    private PtrCardMapper ptrCardMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询巡点
     * 
     * @param cardId
     *            巡点ID
     * @return 巡点
     */
    @Override
    public PtrCard selectPtrCardById(Long cardId) {
        return ptrCardMapper.selectPtrCardById(cardId);
    }

    /**
     * 通过二维码查询巡点
     * 
     * @param qrcode
     *            二维码
     * @return 巡点
     */
    @Override
    public List<PtrCard> selectPtrCardByCode(String qrcode, boolean scope) {
        List<PtrCard> list = ptrCardMapper.selectPtrCardByCode(qrcode);
        if (list != null && list.size() > 0 && scope) {
            SysUser user = ShiroUtils.getSysUser();
            String deptIdPath = user.getDept().getIdPath();
            for (PtrCard card : list) {
                if (!card.getDeptIdPath().startsWith(deptIdPath)) {
                    throw new RuntimeException("没有该点的巡检权限");
                }
            }
        }
        return list;
    }

    /**
     * 查询重复巡点
     * 
     * @param cardId
     *            巡点ID
     * @param cardQrcode
     *            巡点二维码
     * @return 巡点
     */
    @Override
    public List<PtrCard> selectDupicateByCodeNotId(String cardQrcode, Long cardId) {
        return ptrCardMapper.selectDupicateByCodeNotId(cardId, cardQrcode);
    }

    /**
     * 查询重复巡点
     * 
     * @param ptrCard
     *            巡点ID
     * @return 巡点
     */
    @Override
    public List<PtrCard> selectDupicatePtrCard(PtrCard ptrCard) {
        return ptrCardMapper.selectDupicatePtrCard(ptrCard);
    }

    /**
     * 查询巡点列表
     * 
     * @param ptrCard
     *            巡点
     * @return 巡点
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<PtrCard> selectPtrCardList(PtrCard ptrCard) {
        return ptrCardMapper.selectPtrCardList(ptrCard);
    }

    /**
     * 新增巡点
     * 
     * @param ptrCard
     *            巡点
     * @return 结果
     */
    @Override
    public int insertPtrCard(PtrCard ptrCard) {
        checkQrcode(ptrCard);
        if (ptrCard.getCardId() == null) {
            ptrCard.setCardId(uidWorker.getNextId());
        }
        return ptrCardMapper.insertPtrCard(ptrCard);
    }

    /**
     * 修改巡点
     * 
     * @param ptrCard
     *            巡点
     * @return 结果
     */
    @Override
    public int updatePtrCard(PtrCard ptrCard) {
        checkQrcode(ptrCard);
        return ptrCardMapper.updatePtrCard(ptrCard);
    }

    /**
     * 插入外观图片（来源于日志）
     * 
     * @param cardId
     * @param logId
     * @return 结果
     */
    @Override
    public int insertFacadeFromLog(Long cardId, Long logId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cardId", cardId);
        map.put("logId", logId);
        int result = ptrCardMapper.insertFacadeFromLog(map);
        ptrCardMapper.insertFacadeFromLogMap(map);
        return result;
    }

    /**
     * 删除巡点对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePtrCardByIds(String ids) {
        return ptrCardMapper.deletePtrCardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡点信息
     * 
     * @param cardId
     *            巡点ID
     * @return 结果
     */
    public int deletePtrCardById(Long cardId) {
        return ptrCardMapper.deletePtrCardById(cardId);
    }

    private void checkQrcode(PtrCard ptrCard) {
        if (StringUtils.isBlank(ptrCard.getCardQrcode1()) && StringUtils.isBlank(ptrCard.getCardQrcode2())
                && StringUtils.isBlank(ptrCard.getCardQrcode3()) && StringUtils.isBlank(ptrCard.getCardQrcode4())
                && StringUtils.isBlank(ptrCard.getCardQrcode5())) {
            throw new RuntimeException("至少有一个二维码");
        }
        Object qrcode = StringUtils.findDupicate(ptrCard.getCardQrcode1(), ptrCard.getCardQrcode2(),
                ptrCard.getCardQrcode3(), ptrCard.getCardQrcode4(), ptrCard.getCardQrcode5());
        if (qrcode != null) {
            throw new RuntimeException("二维码重复：" + qrcode);
        }
        checkQrcode(ptrCard.getCardId(), ptrCard.getCardQrcode1());
        checkQrcode(ptrCard.getCardId(), ptrCard.getCardQrcode2());
        checkQrcode(ptrCard.getCardId(), ptrCard.getCardQrcode3());
        checkQrcode(ptrCard.getCardId(), ptrCard.getCardQrcode4());
        checkQrcode(ptrCard.getCardId(), ptrCard.getCardQrcode5());
    }

    private void checkQrcode(Long cardId, String cardQrcode) {
        if (StringUtils.isNotBlank(cardQrcode)) {
            List<PtrCard> list = selectDupicateByCodeNotId(cardQrcode, cardId);
            if (list != null && list.size() > 0) {
                throw new RuntimeException("二维码已经存在：" + cardQrcode);
            }
        }
    }

    @Override
    @Transactional
    public int importData(MultipartFile file, boolean updateSupport) throws Exception {

        SysUser user = ShiroUtils.getSysUser();
        Long deptId = user.getDept().getDeptId();
        String loginName = user.getLoginName();
        Date time = new Date();

        List<SysDictData> typeList = dictDataMapper.selectDictDataByType("patrol_card_type");
        List<SysDictData> lineList = dictDataMapper.selectDictDataByType("patrol_line_type");
        List<SysDictData> sideList = dictDataMapper.selectDictDataByType("patrol_side_type");

        boolean emptyType = false;
        StringBuilder typeError = new StringBuilder();
        StringBuilder lineError = new StringBuilder();
        StringBuilder sideError = new StringBuilder();

        Map<String, String> qrcodes = new HashMap<>();

        ExcelUtil<PtrCard> util = new ExcelUtil<PtrCard>(PtrCard.class);
        List<PtrCard> cardList = util.importExcel(file.getInputStream());
        for (PtrCard card : cardList) {
            checkQrcode(card);
            checkQrcode(card, qrcodes);
            if (checkTypeName(card, typeList, typeError)) {
                emptyType = true;
            }
            // checkLineName(card, lineList, lineError);
            checkSideName(card, sideList, sideError);

            card.setCardId(uidWorker.getNextId());
            card.setCreateBy(loginName);
            card.setCreateTime(time);
            card.setUpdateBy(loginName);
            card.setUpdateTime(time);
            card.setCardDept(deptId);
            card.setRemark(card.getDesc());
        }
        StringBuilder error = new StringBuilder();
        if (emptyType) {
            error.append("【类型不能为空】");
        }
        if (typeError.length() > 0) {
            error.append("【找不到类型：");
            error.append(typeError.toString());
            error.append("】");
        }
        if (lineError.length() > 0) {
            error.append("【找不到线名：");
            error.append(lineError.toString());
            error.append("】");
        }
        if (sideError.length() > 0) {
            error.append("【找不到行别：");
            error.append(sideError.toString());
            error.append("】");
        }
        for (PtrCard card : cardList) {
            ptrCardMapper.insertPtrCard(card);
        }
        if (error.length() > 0) {
            throw new Exception(error.toString());
        }
        return cardList.size();
    }

    private void checkQrcode(PtrCard card, Map<String, String> qrcodes) throws Exception {
        String qrcode1 = card.getCardQrcode1();
        if (StringUtils.isNotBlank(qrcode1)) {
            String exists = qrcodes.put(qrcode1, qrcode1);
            if (exists != null) {
                throw new Exception("二维码重复：" + qrcode1);
            }
        }
        String qrcode2 = card.getCardQrcode2();
        if (StringUtils.isNotBlank(qrcode2)) {
            String exists = qrcodes.put(qrcode2, qrcode2);
            if (exists != null) {
                throw new Exception("二维码重复：" + qrcode2);
            }
        }
        String qrcode3 = card.getCardQrcode3();
        if (StringUtils.isNotBlank(qrcode3)) {
            String exists = qrcodes.put(qrcode3, qrcode3);
            if (exists != null) {
                throw new Exception("二维码重复：" + qrcode3);
            }
        }
        String qrcode4 = card.getCardQrcode4();
        if (StringUtils.isNotBlank(qrcode4)) {
            String exists = qrcodes.put(qrcode4, qrcode4);
            if (exists != null) {
                throw new Exception("二维码重复：" + qrcode4);
            }
        }
        String qrcode5 = card.getCardQrcode5();
        if (StringUtils.isNotBlank(qrcode5)) {
            String exists = qrcodes.put(qrcode5, qrcode5);
            if (exists != null) {
                throw new Exception("二维码重复：" + qrcode5);
            }
        }
    }

    private boolean checkTypeName(PtrCard card, List<SysDictData> typeList, StringBuilder msg) {
        String type = card.getCardType();
        if (StringUtils.isBlank(type)) {
            return true;
        }
        for (SysDictData dict : typeList) {
            if (dict.getDictLabel().equals(type)) {
                card.setCardType(dict.getDictValue());
                return false;
            }
        }
        if (msg.length() > 0) {
            msg.append(", ");
        }
        msg.append(type);
        return false;
    }

    private void checkLineName(PtrCard card, List<SysDictData> lineList, StringBuilder msg) {
        String line = card.getCardLine();
        if (StringUtils.isBlank(line)) {
            return;
        }
        for (SysDictData dict : lineList) {
            if (dict.getDictLabel().equals(line)) {
                card.setCardLine(dict.getDictValue());
                return;
            }
        }
        if (msg.length() > 0) {
            msg.append(", ");
        }
        msg.append(line);
    }

    private void checkSideName(PtrCard card, List<SysDictData> sideList, StringBuilder msg) {
        String side = card.getCardSide();
        if (StringUtils.isBlank(side)) {
            return;
        }
        for (SysDictData dict : sideList) {
            if (dict.getDictLabel().equals(side)) {
                card.setCardSide(dict.getDictValue());
                return;
            }
        }
        if (msg.length() > 0) {
            msg.append(", ");
        }
        msg.append(side);
    }
}