package com.zjcjava.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.Configuration;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Gener {

	 /**
	  * @param args
	  */
	 public static void main(String[] args) {
	  List<String> warnings = new ArrayList<String>();
	  boolean overwrite = true;
	  File configFile = new File("config/generatorConfig.xml");
	  ConfigurationParser cp = new ConfigurationParser(warnings);
	  org.mybatis.generator.config.Configuration config;
	  try {
	   config = cp.parseConfiguration(configFile);

	   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	   MyBatisGenerator myBatisGenerator;
	   try {
	    myBatisGenerator = new MyBatisGenerator(config, callback,
	      warnings);
	    myBatisGenerator.generate(null);
	   } catch (Exception e) {

	    e.printStackTrace();
	   }

	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (XMLParserException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 }
	 

}
