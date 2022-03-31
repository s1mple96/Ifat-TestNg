package com.meiji.biz.api

import com.miyuan.ifat.support.factory.FactorySupport
import groovy.sql.Sql

class MysqlAPI {
    @Lazy
    static Sql platformGoodsSql = FactorySupport.createSql("mysql-platform-goods")
    @Lazy
    static Sql prod_meiji_shop = FactorySupport.createSql("meiji_shop")
    @Lazy
    static Sql prod_meiji_dealer = FactorySupport.createSql("meiji_dealer")
    @Lazy
    static Sql prod_meiji_user = FactorySupport.createSql("meiji_user")
    @Lazy
    static Sql prod_meiji_excel = FactorySupport.createSql("meiji_excel")
    @Lazy
    static Sql prod_meiji_order = FactorySupport.createSql("meiji_order")
    @Lazy
    static Sql prod_meiji_pay = FactorySupport.createSql("meiji_pay")
    @Lazy
    static Sql prod_meiji_settlement = FactorySupport.createSql("meiji_settlement")
    @Lazy
    static Sql prod_meiji_stock = FactorySupport.createSql("meiji_stock")
    @Lazy
    static Sql prod_meiji_product = FactorySupport.createSql("meiji_product")
    @Lazy
    static Sql prod_meiji_supplier = FactorySupport.createSql("meiji_supplier")
    @Lazy
    static Sql prod_meiji_message = FactorySupport.createSql("meiji_message")
//    static Sql prod_meiji_office_account = FactorySupport.createSql("meiji_office_account")
    @Lazy
    static Sql prod_meiji_active = FactorySupport.createSql("meiji_active")
}
