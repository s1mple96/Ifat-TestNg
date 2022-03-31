package com.meiji.biz.request.http.lottery.address

import com.meiji.biz.request.http.lottery.LotPost

/**
 *
 @author s1mple
 @create 2022/2/10-11:15
 */
class AddressPage extends LotPost{
    {
        super.api = "/lottery/user/address/page"
        super.params = ["condition", "order", "page", "rows", "sort"]
    }
}
