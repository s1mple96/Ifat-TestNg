package com.miyuan.ifat.support.test

import com.miyuan.ifat.support.annotation.Data
import com.miyuan.ifat.support.constants.TestConstants
import com.miyuan.ifat.support.util.ResourceUtil
import org.apache.commons.lang3.StringUtils
import org.testng.SkipException
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import java.lang.annotation.Annotation
import java.lang.reflect.Method

class TestData {
    @DataProvider(name = "dataProvider")
    TestContext[] testData(Method method) {
        String testName = ""
        String testDescription = ""
        String dataFile = ""
        String dataTestName = ""
        String commonFile = ""
        Annotation[] annotations = method.getDeclaredAnnotations()
        for(Annotation annotation:annotations){
            if (annotation.annotationType().typeName == Test.typeName){
                Test test = (Test)annotation
                testName = test.testName()
                testDescription = test.description()
            }else if(annotation.annotationType().typeName== Data.typeName){
                Data data = (Data)annotation
                commonFile = data.commonFile()
                dataFile = data.dataFile()
                dataTestName = data.testName()
            }
        }
        commonFile = StringUtils.isNotEmpty(commonFile)?commonFile:TestConstants.commonDataFile
        Map commonData = ResourceUtil.getCommonData(commonFile)
        testName = StringUtils.isNotEmpty(dataTestName)?dataTestName:testName
        if(testName==null){
            throw new SkipException("用例名称为空")
        }
        Map testData = ResourceUtil.getTestData(dataFile,testName)
        TestContext testContext = new TestContext()
        commonData.each {x->
            testContext.put(x.key,x.value)
        }
        testData.each {x->
            testContext.put(x.key,x.value)
        }
        testContext.put("description",testDescription)
        return [testContext]
    }

    @DataProvider(name = "common")
    TestContext[] CommonData(Method method) {
        Map commonData = ResourceUtil.getCommonData(TestConstants.commonDataFile)
        TestContext testContext = new TestContext()
        commonData.each {x->
            testContext.put(x.key,x.value)
        }
        testContext.put("description",method.getName())
        return [testContext]
    }


    public static TestContext getCommonData() {
        Map commonData = ResourceUtil.getCommonData(TestConstants.commonDataFile)
        TestContext testContext = new TestContext()
        commonData.each {x->
            testContext.put(x.key,x.value)
        }
        return testContext
    }


    public static TestContext getTestData(String testName) {
        Map testData = ResourceUtil.getTestData(null,testName)
        TestContext testContext = new TestContext()
        testData.each {x->
            testContext.put(x.key,x.value)
        }
        return testContext
    }

}
