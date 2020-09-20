package com.feather.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.constant.GenConstants;
import com.feather.common.core.text.Convert;
import com.feather.common.exception.BusinessException;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.StringUtils;
import com.feather.generator.domain.GenTable;
import com.feather.generator.domain.GenTableColumn;
import com.feather.generator.mapper.GenTableColumnMapper;
import com.feather.generator.mapper.GenTableMapper;
import com.feather.generator.service.IGenTableService;
import com.feather.generator.util.GenUtils;
import com.feather.generator.util.VelocityInitializer;
import com.feather.generator.util.VelocityUtils;

/**
 * 业务 服务层实现
 *
 * @author feather
 */
@Service
public class GenTableServiceImpl implements IGenTableService {
    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询业务信息
     *
     * @param id
     *            业务ID
     * @return 业务信息
     */
    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 查询业务列表
     *
     * @param genTable
     *            业务信息
     * @return 业务集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param genTable
     *            业务信息
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return genTableMapper.selectDbTableList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param tableNames
     *            表名称组
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 修改业务
     *
     * @param genTable
     *            业务信息
     * @return 结果
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable) {
        String options = JSONObject.toJsonString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteGenTableByIds(String ids) {
        genTableMapper.deleteGenTableByIds(Convert.toLongArray(ids));
        genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }

    /**
     * 导入表结构
     *
     * @param tableList
     *            导入表列表
     * @param operName
     *            操作人员
     */
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList, String operName) {
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                table.setTableId(uidWorker.getNextId());
                Long tableId = table.getTableId();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        column.setColumnId(uidWorker.getNextId());
                        table.setTableId(tableId);
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 预览代码
     *
     * @param tableId
     *            表编号
     * @return 预览数据列表
     */
    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // 查询列信息
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * 生成代码
     *
     * @param tableName
     *            表名称
     * @return 数据
     * @throws IOException
     */
    @Override
    public byte[] generatorCode(String tableName) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            generatorCode(tableName, zip);
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * 批量生成代码
     *
     * @param tableNames
     *            表数组
     * @return 数据
     * @throws IOException
     */
    @Override
    public byte[] generatorCode(String[] tableNames) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            for (String tableName : tableNames) {
                generatorCode(tableName, zip);
            }
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * 查询表信息并生成代码
     * 
     * @throws IOException
     */
    private void generatorCode(String tableName, ZipOutputStream zip) throws IOException {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 查询列信息
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new IOException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 修改保存参数校验
     *
     * @param genTable
     *            业务信息
     */
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSONObject.toJsonString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parse(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new BusinessException("树编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new BusinessException("树父编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new BusinessException("树名称字段不能为空");
            }
        }
    }

    /**
     * 设置主键列信息
     *
     * @param genTable
     *            业务表信息
     * @param columns
     *            业务字段列表
     */
    public void setPkColumn(GenTable table, List<GenTableColumn> columns) {
        for (GenTableColumn column : columns) {
            if (column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if (table.getPkColumn() == null) {
            table.setPkColumn(columns.get(0));
        }
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable
     *            设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parse(genTable.getOptions());
        if (StringUtils.isNotEmpty(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }
}