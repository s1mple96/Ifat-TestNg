package com.meiji.test.debug

import org.testng.annotations.Test

class UtilDebug {
    @Test
    void test1(){
        println(DateUtil.dateAdd(new Date(),Calendar.DAY_OF_YEAR,1))
    }
}
