package com.meiji.biz.service

import org.apache.dubbo.config.ApplicationConfig
import org.apache.dubbo.config.ReferenceConfig
import org.apache.dubbo.config.RegistryConfig
import org.apache.dubbo.config.utils.ReferenceConfigCache
import org.apache.dubbo.rpc.RpcContext
import org.apache.dubbo.rpc.service.GenericService

class DubboService {
    static Object invoke(String address,String interfaceName,String methodName,String version,String group,Integer timeOut,String[] paramTypes,Object[] params,Object customData){
        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig()
        application.setName("test22")

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig()
        registry.setAddress(address)
        //registry.setGroup("DEFAULT_GROUP")

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>()
        reference.setApplication(application)
        reference.setRegistry(registry)
        reference.setInterface(interfaceName)
        reference.setVersion(version)
        reference.setTimeout(timeOut)
        reference.setGroup(group)
        // 声明为泛化接口
        reference.setGeneric("true");

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        RpcContext.getContext().setAttachment("customData", customData);
        Object result = genericService.$invoke(methodName, paramTypes,params);
        return result;
    }
}
