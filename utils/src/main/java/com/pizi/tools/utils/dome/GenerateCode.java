package com.pizi.tools.utils.dome;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * 代码生成工具类
 * 用于自动化生成MyBatis Plus相关代码，包括实体类、Mapper、Service等。
 */
public class GenerateCode {
    /**
     * 主函数，执行代码生成流程。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        //实例化AutoGenerator
        AutoGenerator autoGenerator = new AutoGenerator();
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 数据库类型
        dataSourceConfig.setDbType(DbType.MYSQL);
        // url、用户名、密码
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/utils?useUnicode=true&characterEncoding=UTF-8");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        //设置驱动名称 这里要注意mysql驱动的版本5.x
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        autoGenerator.setDataSource(dataSourceConfig);

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //设置输出路径
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        //是否打开输出目录
        globalConfig.setOpen(false);
        //作者
        globalConfig.setAuthor("痞子");
        //设置Service命名格式 去掉IService的I
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        //包信息
        PackageConfig packageConfig = new PackageConfig();
        //设置父包名
        packageConfig.setParent("com.pizi.tools");
        //设置实体类包名
        packageConfig.setEntity("entity");
        //有时候把持久层记为dao或repository
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //设置要映射的表名（字符串用逗号隔开，多表）
        strategyConfig.setInclude("t_user_table");
        //设置命名策略，将下划线命名转换为驼峰命名
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 开启Lombok模型
        strategyConfig.setEntityLombokModel(true);
        //设置逻辑删除字段名
        strategyConfig.setLogicDeleteFieldName("deleted");
        //自动填充配置
        TableFill tableFill1 = new TableFill("create_time", FieldFill.INSERT);
        TableFill tableFill2 = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        List<TableFill> list = Arrays.asList(tableFill1, tableFill2);
        strategyConfig.setTableFillList(list);
        //设置乐观锁字段名
        strategyConfig.setVersionFieldName("version");
        autoGenerator.setStrategy(strategyConfig);

        //启动
        autoGenerator.execute();
    }

}
