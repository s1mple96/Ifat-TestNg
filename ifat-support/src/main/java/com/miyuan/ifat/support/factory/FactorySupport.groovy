package com.miyuan.ifat.support.factory

import com.alibaba.fastjson.JSON
import com.alibaba.nacos.api.NacosFactory
import com.alibaba.nacos.api.PropertyKeyConst
import com.alibaba.nacos.api.config.ConfigService
import com.miyuan.ifat.support.util.HttpUtil
import com.miyuan.ifat.support.util.ResourceUtil
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import groovy.sql.Sql
import org.apache.http.HttpHost
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

class FactorySupport {
    static Map<String, Object> factory = new HashMap<String, Object>()
    static Sql createSql(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as Sql
        }
        Map map = ResourceUtil.getBeanData(beanName)
        String url = map.get("url")
        String user = map.get("user")
        String password = map.get("password")
        String driver = map.get("driver")
        url = url + "?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&tinyInt1isBit=false"
        Sql sql = Sql.newInstance(url, user, password, driver)
        factory.put(beanName,sql)
        return sql
    }

    static JedisCluster createRedis(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as JedisCluster
        }
        Map map = ResourceUtil.getBeanData(beanName)
        String hosts = map.get("host")
        Set<HostAndPort> jedisClusterSet = new HashSet<HostAndPort>()
        List hostList = hosts.split(",")
        for(String host:hostList){
            jedisClusterSet.add(new HostAndPort(host.split(":")[0],Integer.parseInt(host.split(":")[1])))
        }
        JedisCluster jedisCluster = new JedisCluster(jedisClusterSet)
        factory.put(beanName,jedisCluster)
        return jedisCluster
    }


    static RestHighLevelClient createHighLevelEs(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as RestHighLevelClient
        }
        List<HttpHost> hosts = new ArrayList<>()
        Map map = ResourceUtil.getBeanData(beanName)
        String hostName = map.get("hostName")
        Integer port = Integer.parseInt(map.get("port"))
        String scheme = map.get("scheme")
        String[] hostNames = hostName.split(",")
        for (String esIp : hostNames) {
            HttpHost httpHost =new HttpHost(esIp,port,scheme)
            hosts.add(httpHost)
        }
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(hosts as HttpHost[]))
        factory.put(beanName,client)
        return client
    }


    static MongoDatabase createMongoCollection(String beanName) {
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as MongoDatabase
        }
        Map map = ResourceUtil.getBeanData(beanName)
        Integer connectionsPerHost  = Integer.parseInt(map.get("connectionsPerHost"))
        Integer maxWaitTime  = Integer.parseInt(map.get("maxWaitTime"))
        Integer connectTimeout  = Integer.parseInt(map.get("connectTimeout"))
        String host = map.get("host")
        Integer port = Integer.parseInt(map.get("port"))
        String userName = map.get("userName")
        String dataBase = map.get("dataBase")
        String password = map.get("password")
        ServerAddress serverAddress = new ServerAddress(host, port)
        MongoCredential credential = MongoCredential.createCredential(userName, dataBase, password.toCharArray())
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder()
        builder.connectionsPerHost(connectionsPerHost)
        builder.maxWaitTime(maxWaitTime)
        builder.connectTimeout(connectTimeout)
        MongoClientOptions mongoClientOptions = builder.build()
        MongoClient monGoClient = new MongoClient(serverAddress, Arrays.asList(credential), mongoClientOptions)

        MongoDatabase mongoDatabase = monGoClient.getDatabase(dataBase)

        factory.put(beanName,mongoDatabase)
        return mongoDatabase
    }

    static Channel createRabbitMq(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as Channel
        }
        Map map = ResourceUtil.getBeanData(beanName)
        String host = map.get("host")
        String username = map.get("username")
        String password = map.get("password")
        Integer port = Integer.parseInt(map.get("port"))

        ConnectionFactory connectionFactory = new ConnectionFactory()
        connectionFactory.setHost(host)
        connectionFactory.setUsername(username)
        connectionFactory.setPassword(password)
        connectionFactory.setPort(port)
        Connection connection = connectionFactory.newConnection()
        Channel channel = connection.createChannel()
        factory.put(beanName,channel)
        return channel
    }

    static RabbitTemplate createRabbitTemplate(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as Channel
        }
        Map map = ResourceUtil.getBeanData(beanName)
        String addresses = map.get("addresses")
        String username = map.get("username")
        String password = map.get("password")

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses)
        connectionFactory.setUsername(username)
        connectionFactory.setPassword(password)
        connectionFactory.setVirtualHost("/")
        // 如果需要confirm则设置为true
        connectionFactory.setPublisherConfirms(true)
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory)
        rabbitTemplate.setMandatory(true)
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
              if (ack) {//true表示消息发送成功
                   print("消息发送成功");
                  } else {
                   print("消息发送失败:" + cause)
                  }
            }
        )
        return rabbitTemplate
    }



    static DefaultMQProducer createRocketMq(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as DefaultMQProducer
        }
        Map map = ResourceUtil.getBeanData(beanName)
        String producerGroup = map.get("producerGroup")
        String namesrvAddr = map.get("namesrvAddr")
        Integer retryTimesWhenSendFailed = Integer.parseInt(map.get("retryTimesWhenSendFailed"))
        Integer sendMsgTimeout = Integer.parseInt(map.get("sendMsgTimeout"))

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerGroup)
        defaultMQProducer.setNamesrvAddr(namesrvAddr)
        defaultMQProducer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed)
        defaultMQProducer.setSendMsgTimeout(sendMsgTimeout)
        try {
            defaultMQProducer.start()
        } catch (Exception e) {
            e.printStackTrace()
        }
        factory.put(beanName,defaultMQProducer)
        return defaultMQProducer
    }

    static ConfigService createNacos(String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as ConfigService
        }
        Map map = ResourceUtil.getBeanData(beanName)

        String serverAddr = map.get("serverAddr")
        String namespace = map.get("namespace")
        Properties properties = new Properties()
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr)
        properties.put(PropertyKeyConst.NAMESPACE,namespace)
        ConfigService configService = NacosFactory.createConfigService(properties)
        factory.put(beanName,configService)
        return configService
    }
//创建apllo 链接
    static Map createAplloClient (String beanName){
        if(factory.get(beanName)!=null){
            return factory.get(beanName) as Map
        }
        Map map = ResourceUtil.getBeanData(beanName)
        if(map.size()==0){
            return null
        }
        String url = map.get("serverAddr")
        Map heads = new HashMap()
        Map req = new HashMap()
        String StrRes = HttpUtil.get(url,heads,req)
        Map res= JSON.parseObject(StrRes, HashMap.class).configurations
        factory.put(beanName,res)
        return res
    }
}
