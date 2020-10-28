package com.huali.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //主机地址;默认为 localhost
        connectionFactory.setHost("192.168.126.131");
        //连接端口;默认为 5672
        connectionFactory.setPort(5672);
        //虚拟主机名称;默认为 /
        connectionFactory.setVirtualHost("huali");
        //连接用户名；默认为guest
        connectionFactory.setUsername("huali");
        //连接密码；默认为guest
        connectionFactory.setPassword("huali");

        //创建连接
        return connectionFactory.newConnection();
    }
}
