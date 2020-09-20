package com.feather.lpscommunity.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.core.text.Convert;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.framework.shiro.service.SysPasswordService;
import com.feather.framework.util.ShiroUtils;
import com.feather.lpscommunity.domain.ScRegister;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.mapper.ScRegisterMapper;
import com.feather.lpscommunity.service.IScRegisterService;
import com.feather.lpscommunity.service.IScVolunteerService;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.mapper.PrdAttachmentMapper;
import com.feather.prd.service.IPrdAttachmentService;
import com.feather.system.domain.SysDept;
import com.feather.system.domain.SysRole;
import com.feather.system.domain.SysUser;
import com.feather.system.mapper.SysDeptMapper;
import com.feather.system.mapper.SysDictDataMapper;
import com.feather.system.mapper.SysUserMapper;
import com.feather.system.service.ISysConfigService;
import com.feather.system.service.ISysRoleService;
import com.feather.system.service.ISysUserService;

/**
 * 志愿者注册Service业务层处理
 *
 * @author fancy
 * @date 2019-11-14
 */
@Service
public class ScRegisterServiceImpl implements IScRegisterService {
    @Autowired
    private ScRegisterMapper scRegisterMapper;

    @Autowired
    private PrdAttachmentMapper prdAttachmentMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IPrdAttachmentService prdAttachmentService;

    @Autowired
    private IScVolunteerService scVolunteerService;

    /**
     * 查询志愿者注册
     *
     * @param registerId
     *            志愿者注册ID
     * @return 志愿者注册
     */
    @Override
    public ScRegister selectScRegisterById(Long registerId) {
        return scRegisterMapper.selectScRegisterById(registerId);
    }

    /**
     * 查询志愿者注册列表
     *
     * @param scRegister
     *            志愿者注册
     * @return 志愿者注册
     */
    @Override
    public List<ScRegister> selectScRegisterList(ScRegister scRegister) {
        return scRegisterMapper.selectScRegisterList(scRegister);
    }

    @Override
    public List<Map<String, Object>> screenScRegisterList(ScRegister scRegister, String headUrl) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<ScRegister> scRegisterList = this.selectScRegisterList(scRegister);
        for (int i = 0; i < scRegisterList.size(); i++) {
            ScRegister scRegister1 = scRegisterList.get(i);
            PrdAttachment prdAttachment = prdAttachmentMapper.selectPrdAttachmentById(scRegister1.getRegisterId());
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("register_id", scRegister1.getRegisterId());
            map.put("headsculpture", prdAttachment == null ? "" : headUrl + prdAttachment.getFilePath());
            map.put("register_name", scRegister1.getRegisterName());
            map.put("register_tel", scRegister1.getRegisterTel());
            map.put("register_synopsis", scRegister1.getRegisterSynopsis());
            map.put("register_sex", scRegister1.getRegisterSex() == "" ? ""
                    : sysDictDataMapper.selectDictLabel("sys_user_sex", scRegister1.getRegisterSex()));
            map.put("register_profession", scRegister1.getRegisterProfession());
            map.put("audit_time", scRegister1.getAuditTime());
            map.put("aduit_state", scRegister1.getAuditState() == "" ? ""
                    : sysDictDataMapper.selectDictLabel("sc_audit_status", scRegister1.getAuditState()));
            map.put("aduit_pass_state", scRegister1.getAuditPassState() == "" ? ""
                    : sysDictDataMapper.selectDictLabel("sc_audit_type", scRegister1.getAuditPassState()));
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public int insertScRegister(ScRegister scRegister) {
        return scRegisterMapper.insertScRegister(scRegister);
    }

    /**
     * 新增志愿者注册
     *
     * @param scRegister
     *            志愿者注册
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScRegister(ScRegister scRegister, MultipartFile headsculpture) throws Exception {
        scRegister.setAuditPassState(""); // 审核通过状态位空
        // 存头像文件
        if (headsculpture != null) {
            // 获取头像
            String url = FileUploadUtils.upload(headsculpture, null, true);
            PrdAttachment prdAttachment = new PrdAttachment();
            prdAttachment.setFileId(scRegister.getRegisterId());
            prdAttachment.setFileName(headsculpture.getOriginalFilename());
            prdAttachment.setFilePath(url);
            prdAttachment.setFileSize(headsculpture.getSize());
            prdAttachmentService.insertPrdAttachment(prdAttachment);
        }
        // 存入注册表
        return scRegisterMapper.insertScRegister(scRegister);
    }

    /**
     * 志愿者审核
     * 
     * @param scRegister
     *            志愿者审核
     * @return
     */
    @Transactional
    @Override
    public int editScRegisterAudit(ScRegister scRegister) {
        // 唯一id
        Long id = scRegister.getRegisterId();
        // 根据提交注册id获取注册详情信息
        ScRegister scRegister2 = scRegisterMapper.selectScRegisterById(id);
        // 获取头像路径
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(id);
        // 如果审核通过
        if ("0".equals(scRegister.getAuditPassState())) {
            String lognName = scRegister2.getRegisterTel();
            // 获取志愿者角色
            SysRole role = roleService.selectRoleByRoleKey("volunteer");
            // 获取部门信息
            SysDept dept = sysDeptMapper.selectDeptById(101L);
            // 就生成志愿者用户信息
            SysUser sysUser = new SysUser();
            sysUser.setUserId(id);
            sysUser.setUserName(scRegister2.getRegisterName());
            sysUser.setLoginName(lognName);
            sysUser.setSex(scRegister2.getRegisterSex());
            sysUser.setAvatar(prdAttachment.getFilePath());
            sysUser.setSalt(ShiroUtils.randomSalt());
            sysUser.setPassword(passwordService.encryptPassword(lognName,
                    configService.selectConfigByKey("sys.user.initPassword"), sysUser.getSalt()));
            sysUser.setPhonenumber(lognName);
            // 设置角色
            sysUser.setRoleId(role.getRoleId());
            // 设置部门
            sysUser.setDept(dept);
            sysUser.setDeptId(dept.getDeptId());
            sysUserMapper.insertUser(sysUser);
            // 插入角色用户表
            roleService.insertAuthUsers(role.getRoleId(), sysUser.getUserId() + "");

            // 插入志愿者信息表
            ScVolunteer scVolunteer = new ScVolunteer(id, scRegister2.getRegisterTel(), sysUser.getLoginName(),
                    scRegister2.getRegisterName(), scRegister2.getRegisterSex(), scRegister2.getRegisterBir(),
                    scRegister2.getRegisterIDCard(), scRegister2.getRegisterAddress(),
                    scRegister2.getRegisterProfession(), scRegister2.getRegisterSynopsis());
            // 头像
            scVolunteer.setVolunteerAvatar(prdAttachment.getFilePath());

            // 插入志愿者表
            scVolunteerService.insertScVolunteer(scVolunteer);
        }
        // 修改注册志愿者的审核状态
        return scRegisterMapper.updateScRegister(scRegister);
    }

    /**
     * 修改志愿者注册
     *
     * @param scRegister
     *            志愿者注册
     * @return 结果
     */
    @Override
    public int updateScRegister(ScRegister scRegister) {
        return scRegisterMapper.updateScRegister(scRegister);
    }

    /**
     * 删除志愿者注册对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScRegisterByIds(String ids) {
        try {
            // 删除志愿者信息
            scVolunteerService.deleteScVolunteerByIds(ids);
            // 删除用户表信息
            userService.deleteUserByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scRegisterMapper.deleteScRegisterByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除志愿者注册信息
     *
     * @param registerId
     *            志愿者注册ID
     * @return 结果
     */
    @Override
    public int deleteScRegisterById(Long registerId) {
        try {
            // 删除志愿者信息
            scVolunteerService.deleteScVolunteerById(registerId);
            // 删除用户表信息
            userService.deleteUserById(registerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scRegisterMapper.deleteScRegisterById(registerId);
    }

    @Override
    public ScRegister checkAccountUnique(ScRegister register) {
        return scRegisterMapper.checkAccountUnique(register);
    }

}