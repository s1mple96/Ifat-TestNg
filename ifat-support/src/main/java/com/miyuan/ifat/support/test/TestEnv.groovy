package com.miyuan.ifat.support.test

import com.miyuan.ifat.support.util.ResourceUtil

class TestEnv {
    public static String getEnv(){
        String vmEnv = System.getProperty("env")
        String propertiesEnv = ResourceUtil.getEnvConfig("env")
        String env = vmEnv==null ? propertiesEnv:vmEnv
        if (env == null){
            new Exception("请配置测试环境")
        }
        return env
    }

    public static String isGray(){
        String vmIsGray = System.getProperty("isGray")
        String propertiesIsGray = ResourceUtil.getEnvConfig("isGray")
        String isGray = vmIsGray==null ? propertiesIsGray:vmIsGray
        if (isGray == null){
            return "false"
        }
        return isGray
    }

    public static String logger(){
        String vmIsLogger = System.getProperty("logger")
        String propertiesIsLogger = ResourceUtil.getEnvConfig("logger")
        String isLogger = vmIsLogger==null ? propertiesIsLogger:vmIsLogger
        if (isLogger == null){
            return "true"
        }
        return isLogger
    }

    public static String isDingTalk(){
        String vmIsDingTalk = System.getProperty("isDingTalk")
        String propertiesIsDingTalk = ResourceUtil.getEnvConfig("isDingTalk")
        String IsDingTalk = vmIsDingTalk==null ? propertiesIsDingTalk:vmIsDingTalk
        if (IsDingTalk == null){
            return "false"
        }
        return IsDingTalk
    }

    public static String getJenkins(){
        String jenkinsUrl = ResourceUtil.getEnvConfig("jenkinsUrl")
        return jenkinsUrl
    }

    public static String getSysProperty(String key){
        String property = System.getProperty(key)
        return property
    }
}
