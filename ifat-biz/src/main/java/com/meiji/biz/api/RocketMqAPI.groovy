package com.meiji.biz.api

import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.common.message.Message
import com.miyuan.ifat.support.factory.FactorySupport
import org.apache.rocketmq.client.producer.DefaultMQProducer

class RocketMqAPI {
    @Lazy
    static DefaultMQProducer defaultMQProducer = FactorySupport.createRocketMq("rocketmq-miyuan")

    static void send(String topic,Object body){
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream()
        ObjectOutputStream out = new ObjectOutputStream(byteOut)
        out.writeObject(body)
        Message message = new Message(topic, "",byteOut.toByteArray())
        SendResult sendResult = defaultMQProducer.send(message,50000)
        println(sendResult.msgId)
    }
}
