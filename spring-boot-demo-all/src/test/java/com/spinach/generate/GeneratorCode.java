package com.spinach.generate;

import java.net.URL;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.spinach.example.common.utils.StringUtil;

/**
 * <p>
 *     测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class GeneratorCode {
	private static String parenePath="";
	
	static{
		URL url = GeneratorCode.class.getResource("");
		parenePath = url.getPath().substring(0, url.getPath().indexOf("target"))+"src/main/java";
    	System.out.println(parenePath);
    	if(StringUtil.isNull(parenePath)){
    		parenePath = "src/main/java";
    	}
	}
	
    @Test
    public void generateCode(){
        String packageName = "com.spinach.example.mybatisplus";
        generateByTables(packageName, "system_user","user");
    }
   
    private void generateByTables(String packageName, String... tableNames){
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/test";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("")
                .setPassword("")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("spinach")
                .setOutputDir(parenePath)
                .setFileOverride(true);
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setMapper("dao")
                                .setXml("dao")
                                .setEntity("entity")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setController("controller")
                ).execute();
    }
    
    public static void main(String[] args) {
    	URL url = GeneratorCode.class.getResource("");
    	String path = url.getPath().substring(0, url.getPath().indexOf("target"))+"src/main/java";
    	
    	System.out.println(path);
    	
    	
    	System.out.println(url.getPath());
    	
	}
}
