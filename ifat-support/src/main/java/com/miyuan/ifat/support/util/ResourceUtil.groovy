package com.miyuan.ifat.support.util

import com.miyuan.ifat.support.constants.TestConstants
import com.miyuan.ifat.support.test.TestEnv
import groovy.io.FileType
import groovy.xml.XmlParser
import org.apache.commons.lang3.StringUtils
import org.codehaus.groovy.reflection.ReflectionUtils
import org.joda.time.DateTime
import org.testng.SkipException

class ResourceUtil {
    public static String getSourcePath(){
        String path =  URLDecoder.decode(ReflectionUtils.getClassLoader().getResource(".").getPath(),"utf-8")
        return path
    }

    public static String getEnvDataPath(){
        String resourcePath = getSourcePath()
        String env = TestEnv.getEnv()
        if(env.startsWith("uat")){
            env = "uat"
        }
        String path = resourcePath + TestConstants.testDataPath + env + "/"
        return path
    }

    public static String getConfigPath(){
        String resourcePath =  getSourcePath()
        String path = resourcePath + TestConstants.configPath
        return path
    }


    public static List getTestDataFiles() {
        String path = getEnvDataPath()
        def dir = new File(path)
        List fileList = new ArrayList()
        dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.xml/) {
            fileList.add(it)
        }
        return fileList
    }

    public static Map<String,Object> getTestData(String fileName,String key) {
        Map data = new HashMap()
        if(StringUtils.isNotEmpty(fileName)) {
            data = readFile(getEnvDataPath()+fileName, key)
        }else {
            List files = getTestDataFiles()
            for (String file:files){
                data.putAll(readFile(file,key))
            }
        }
        return data
    }

    private static Map readFile(String file,String key){
        Map data = new HashMap()
        XmlParser xmlParser = new XmlParser()
        try {
            def testData =  xmlParser.parse(new FileInputStream(file))
            testData.each { tests ->
                tests.attributes().each { test ->
                    if (test.value == key) {
                        tests.each { params ->
                            data.put(params.attributes().key.toString(), handler(params.attributes().value))
                        }
                    }
                }
            }
        }catch(Exception e){
            throw new SkipException(file + "文件异常")
        }
        return data
    }

    public static Object handler(Object value){
        String tempValue = String.valueOf(value)
        if(tempValue.contains("&")){
            tempValue.replaceAll("&amp;","&").replaceAll("&lt;","<").replaceAll("&gt;",">")
        }
        //时间替换
        Date time= new Date()
        String strTime =new String()
        Integer num = 0
        //yyyy-MM-dd HH:mm:ss
        if(tempValue.startsWith('$date') ){
            if(tempValue=='$date'){
                time= DateTime.now().minusDays(num).toDate()
                strTime= DateUtil.dateToStr(time)

            }else if(tempValue.endsWith("D")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date+','D'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date-','D'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusDays(num).toDate()

            }
            else if(tempValue.endsWith("H")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date+','H'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date-','H'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusHours(num).toDate()

            }
            else if(tempValue.endsWith("M")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date+','M'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date-','M'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusMinutes(num).toDate()

            }
            else if(tempValue.endsWith("S")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date+','S'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$date-','S'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusSeconds(num).toDate()

            }
            strTime= DateUtil.dateToStr(time)
            tempValue=strTime
        }
        //秒的时间戳
        if(tempValue.startsWith('$timestamp') ){
            if(tempValue=='$timestamp'){
            time= DateTime.now().minusDays(num).toDate()

        }else if(tempValue.endsWith("D")){
            if(tempValue.contains('+')){
                num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp+','D'))-1))
            }else if(tempValue.contains('-')){
                num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp-','D'))
            }else{
                println("格式错误~")
            }
            time= DateTime.now().minusDays(num).toDate()

        }
        else if(tempValue.endsWith("H")){
            if(tempValue.contains('+')){
                num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp+','H'))-1))
            }else if(tempValue.contains('-')){
                num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp-','H'))
            }else{
                println("格式错误~")
            }
            time= DateTime.now().minusHours(num).toDate()

        }
        else if(tempValue.endsWith("M")){
            if(tempValue.contains('+')){
                num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp+','M'))-1))
            }else if(tempValue.contains('-')){
                num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp-','M'))
            }else{
                println("格式错误~")
            }
            time= DateTime.now().minusMinutes(num).toDate()

        }
        else if(tempValue.endsWith("S")){
            if(tempValue.contains('+')){
                num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp+','S'))-1))
            }else if(tempValue.contains('-')){
                num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$timestamp-','S'))
            }else{
                println("格式错误~")
            }
            time= DateTime.now().minusSeconds(num).toDate()

        }
            strTime= DateUtil.DateToTimeStamp(DateUtil.dateToStr(time))
            tempValue=strTime
        }
        // 毫秒时间搓
        if(tempValue.startsWith('$msTimestamp') ){
            if(tempValue=='$msTimestamp'){
                time= DateTime.now().minusDays(num).toDate()

            }else if(tempValue.endsWith("D")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp+','D'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp-','D'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusDays(num).toDate()

            }
            else if(tempValue.endsWith("H")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp+','H'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp-','H'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusHours(num).toDate()

            }
            else if(tempValue.endsWith("M")){
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp+','M'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp-','M'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusMinutes(num).toDate()

            }
            else if(tempValue.endsWith("S")){
                if(tempValue.endsWith("MS")){
                    if(tempValue.contains('+')){
                        num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp+','MS'))-1))
                    }else if(tempValue.contains('-')){
                        num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp-','MS'))
                    }else{
                        println("格式错误~")
                    }
                    time= DateTime.now().minusMillis(num).toDate()
                }else{
                if(tempValue.contains('+')){
                    num = (~(Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp+','S'))-1))
                }else if(tempValue.contains('-')){
                    num = Integer.valueOf(StringUtil.splitMiddle(tempValue,'$msTimestamp-','S'))
                }else{
                    println("格式错误~")
                }
                time= DateTime.now().minusSeconds(num).toDate()
                }
            }

            strTime= DateUtil.DatesToStamp(time)
            tempValue=strTime
        }
        //随机数替换
        if(tempValue.startsWith('$float')){
            num= Integer.valueOf(StringUtil.splitData(tempValue,'$float'))*10
            String floatNum=RandomUtil.getRandomYuan(num)
            tempValue=floatNum

        }
        //数字串替换
        if(tempValue.startsWith('$int')){
            String strNum= StringUtil.splitData(tempValue,'$int')
            //数字范围替换
            if(tempValue.contains("-")){
                String []  strNumList=strNum.split("-")
                String floatNum=RandomUtil.getRandomNumber(Integer.valueOf(strNumList[1]),Integer.valueOf(strNumList[0]))
                tempValue=floatNum
            }
            //纯数字串替换
            else {
            num= Long.valueOf(strNum)
            String floatNum=RandomUtil.getRandomNum(num)
            tempValue=floatNum
            }
        }
        //随机汉字替换
        if(tempValue.startsWith('$CN')){
            String cnNum= StringUtil.splitData(tempValue,'$CN')
            String CN=RandomUtil.getRandomJianHan(Integer.valueOf(cnNum))
            tempValue=CN
        }
        //json 格式转换
        if(tempValue.startsWith("[") && tempValue.endsWith("]")){
            try {
                return JsonUtil.objToJsonList(tempValue)
            }catch (Exception e){
                println("jsonList的格式不对错误：" + e.message)
            }
        }else if(tempValue.startsWith("{") && tempValue.endsWith("}")){
            try {
                return JsonUtil.objToJson(tempValue)
            }catch (Exception e){
                println("json的格式不对,错误："+e.message)
            }
        }else{
            return tempValue
        }
    }

    public static Map<String,Object> getCommonData(String commonDataFile) {
        Map<String,Object> map = new HashMap<String,Object>()
        try {
            XmlParser xmlParser = new XmlParser()
            def testData =  xmlParser.parse(new FileInputStream(getEnvDataPath()+ commonDataFile))
            testData.each { tests ->
                tests.attributes().each { test->
                    tests.each { params ->
                        map.put(params.attributes().key.toString(),params.attributes().value)
                    }
                }
            }
        }catch(Exception e){
           throw new SkipException(commonDataFile + "文件异常")
        }
        return map
    }

    public static Map<String,String> getBeanData(String name) {
        Map<String,String> map = new HashMap<String,String>()
        XmlParser xmlParser = new XmlParser()
        String path = getConfigPath()
        String beanFile = "bean-env.xml"
        String fileName = beanFile.replace("env",TestEnv.getEnv())
        String file = path + fileName
        def beanData =  xmlParser.parse(new FileInputStream(file))
        beanData.each { beans ->
            beans.attributes().each { bean->
                if(bean.value== name){
                    beans.each { params ->
                        map.put(params.attributes().key.toString(),params.attributes().value.toString())
                    }
                }
            }
        }
        return map
    }

    public static String getEnvConfig(String key) {
        String path = getSourcePath()
        String file = path + TestConstants.EnvFile
        def props = new Properties()
        props.load(new FileInputStream(new File(file)))
        return props.get(key)
    }

    public static String getFile(String fileName){
        return ReflectionUtils.getClassLoader().getResourceAsStream(fileName).getText("UTF-8")
    }
}
