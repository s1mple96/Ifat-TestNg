package com.miyuan.ifat.support.test

import com.miyuan.ifat.support.util.DingTalkUtil
import com.miyuan.ifat.support.util.JsonUtil
import com.miyuan.ifat.support.util.ResourceUtil
import com.miyuan.ifat.support.vo.Record
import io.qameta.allure.Allure
import org.testng.*

class CustomListener extends TestListenerAdapter{
    static String url = ResourceUtil.getBeanData("dingTalk").url
    static String secret = ResourceUtil.getBeanData("dingTalk").secret
    static String logger = TestEnv.logger()

    @Override
    public void onTestFailure(ITestResult tr) {
        TestContext testContext = tr.getParameters() as TestContext
        record(testContext.get("record") as List)
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        TestContext testContext = tr.getParameters() as TestContext
        record(testContext.get("record")  as List)
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        if(logger=="true") {
            TestContext testContext = tr.getParameters() as TestContext
            record(testContext.get("record") as List)
        }
    }

    @Override
    public void onTestStart(ITestResult tr){
        String env = TestEnv.getEnv()
        if(env.startsWith("uat")){
            env = "uat"
        }
        ITestNGMethod iTestNGMethod = tr.getMethod()
        String[] groups = iTestNGMethod.getGroups()
        if (!groups.contains(env)){
                throw new TestNGException("当前用例groups配置和环境不匹配")
        }
    }

    @Override
    public void onFinish(ITestContext testContext){
        String envUrl = TestEnv.getSysProperty("dinkTalkUrl")
        String envSecret = TestEnv.getSysProperty("dinkTalkSecret")
        String jenkinsUrl = TestEnv.getJenkins()
        String jobName = TestEnv.getSysProperty("JOB_NAME")
        String jobNum = TestEnv.getSysProperty("BUILD_NUMBER")
        String reportUrl = TestEnv.getSysProperty("reportUrl")
        String dinkTalkUrl = envUrl==null?url:envUrl
        String dinkTalkSecret = envSecret==null?secret:envSecret
        if(TestEnv.isDingTalk()=="false" || dinkTalkUrl==null || dinkTalkSecret==null) {
            return
        }
        StringBuilder stringBuilder = new StringBuilder()
        int intPassed = testContext.getPassedTests().size()
        int intFailed = testContext.getFailedTests().size()
        int intSkipped= testContext.getSkippedTests().size()
        int total = intPassed+intFailed+intSkipped
        if(intFailed==0){
            return
        }
        String name = testContext.getName()
        stringBuilder.append("<font color=#000000 size=3 >测试名称：$name </font>  \n")
        stringBuilder.append("<font color=#000000 size=3 >总用例数：$total </font>  \n")
        stringBuilder.append("<font color=#33FF00 size=3 >通过用例数：$intPassed </font>  \n")
        stringBuilder.append("<font color=#FF0000 size=3 >失败用例数：$intFailed </font>  \n")
        stringBuilder.append("<font color=#FFCC33 size=3 >跳过用例数：$intSkipped </font>  \n")
        stringBuilder.append("----------------------------------  \n")
        Set<ITestResult> iTestResultSet1 = testContext.getFailedTests().getAllResults()
        Set<ITestResult> iTestResultSet2 = testContext.getSkippedTests().getAllResults()
        Set<ITestResult> resultSet = new HashSet<>()
        resultSet.addAll(iTestResultSet1)
        resultSet.addAll(iTestResultSet2)
        int size = 0
        resultSet.each { it ->
            size += 1
            if (size%20==0){
                //大于20先发送一批
                stringBuilder.append("<font color=#FF0000 size=3 >未完待续 </font>  \n")
                DingTalkUtil.send(dinkTalkUrl, dinkTalkSecret,"ifat失败告警",stringBuilder.toString())
                stringBuilder = new StringBuilder()
                stringBuilder.append("<font color=#FF0000 size=3 >接上一条 </font>  \n")
            }
            TestContext context = it.getParameters() as TestContext
            String testName = context.description
            stringBuilder.append("<font color=#000000 size=3 >用例名称：$testName </font>  \n ")
            //stringBuilder.append("**用例名称：").append(testName).append("**  \n")
            stringBuilder.append("<font color=#000000 size=2 >失败原因: </font>  \n")
            String message = it.getThrowable().getMessage()
            if (message == null) {
                message = it.getThrowable().toString()
            }
            List<String> str = message.split("\n")
            String first = str[0]
            stringBuilder.append("<font color=#000000 size=2 >$first</font>  \n")
            if (str.size() > 1) {
                for (int i = 1; i < str.size(); i++) {
                    String tempStr = str.get(i)
                    if (tempStr.length() > 80) {
                        continue
                    } else {
                        String detail = tempStr.replaceAll(" ", "&nbsp;")
                        stringBuilder.append("<font color=#000000 size=2 >$detail</font>  \n")
                    }
                }
            }
            stringBuilder.append("----------------------------------  \n")
        }
        if( jenkinsUrl&& jobName && jobNum){
            String jenkinsReportUrl = jenkinsUrl+"/"+jobName+"/"+jobNum+"/allure/"
            stringBuilder.append("[自动化报告地址](").append(jenkinsReportUrl).append(")")
        }
        if(reportUrl){
            stringBuilder.append("[自动化报告地址](").append(reportUrl).append(")")
        }
        DingTalkUtil.send(dinkTalkUrl, dinkTalkSecret, "ifat失败告警",stringBuilder.toString())
    }
    private static void record(List<Record> recordList) {
        for (Record record:recordList){
            println(record.key+":")
            println(JsonUtil.prettyJson(record.value))
            println("---------------------------------------------------------------------")
            //Reporter.log(record.key+":")
            //Reporter.log(JsonUtil.prettyJson(record.value))
            Allure.addAttachment(record.key,JsonUtil.prettyJson(record.value))
        }
    }
}
