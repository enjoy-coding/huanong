package com.feather.common.config;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: screwConfig
 * @Description: TODO 生成数据文档
 * @Author: dufan
 * @Date: 2020/8/31 0031 下午 17:06
 * @Version: v1.0
 */
public class ScrewConfig {

    /**
     * 生成数据库文档
     * @param dataSource 数据源
     * @param type 生成文档类型(HTML文件,WORD文件,Markdown文件)
     * @param file 生成文件路径
     */
    public static void CreateDatasourceDocument(DataSource dataSource, EngineFileType type, String file){
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
                .fileOutputDir(file)
                // 打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(type)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();

        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version("1.0.3")
                .description("生成文档信息描述")
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();

        // 执行生成
        new DocumentationExecute(config).execute();
    }
    /**
     * 配置想要生成的表+ 配置想要忽略的表
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig(){
        // 忽略表名
        List<String> ignoreTableName = Arrays.asList("aa","test_group");
        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = Arrays.asList("aup","sys");
        // 忽略表后缀
        List<String> ignoreSuffix = Arrays.asList("","");

        return ProcessConfig.builder()
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
//                //忽略表名
//                .ignoreTableName(ignoreTableName)
//                //忽略表前缀
//                .ignoreTablePrefix(ignorePrefix)
//                //忽略表后缀
//                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }
}
