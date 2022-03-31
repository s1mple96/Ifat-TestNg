package com.meiji.test.http.lottery.address

import com.meiji.biz.request.http.lottery.address.AddressPage
import com.miyuan.ifat.support.test.BaseTest
import com.miyuan.ifat.support.test.TestContext
import com.miyuan.ifat.support.test.TestData
import org.testng.annotations.Test

/**
 *
 @author s1mple
 @create 2022/2/10-11:16
 */
class AddressPageTest extends BaseTest {
    AddressPage addressPage = new AddressPage()

    @Test(description = "用户收货地址列表", groups = ["prod", "uat"], testName = "AddressPage",
            dataProvider = "dataProvider", dataProviderClass = TestData.class)
    public void test(TestContext testContext) {
        addressPage.invoke(testContext).baseAssert(testContext)
    }
}
