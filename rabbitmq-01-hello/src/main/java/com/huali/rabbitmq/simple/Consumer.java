package com.huali.rabbitmq.simple;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {

    public static void main(String[] args) throws Exception {
        //获取连接
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
        Connection connection = connectionFactory.newConnection();
        
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列：并设置消息处理
        channel.queueDeclare(Producer.QUEUE_NAME,true,false,false,null);
        //监听消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            /*
            consumerTag ：消息者标签，在channel.basicConsume时候可以指定
            envelope: 消息包内容，可从中获取消息id，消息routingkey，交换机，消息和重转标记（收到消息失败后是否需要重新发送）
            properties: 消息属性
            body： 消息
             */
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由key
                System.out.println("路由key为：" + envelope.getRoutingKey());
                //交换机
                System.out.println("交换机为：" + envelope.getExchange());
                //消息id
                System.out.println("消息id为：" + envelope.getDeliveryTag());
                //收到的消息
                System.out.println("接收到的消息：" + new String(body, "UTF-8"));
                System.out.println("");
                System.out.println("================================================================");
                System.out.println("");
            }
        };
        /*
		        监听消息
		        参数一：队列名称
		        参数二：是否自动确认，设置为true表示消息接收到自动向mq回复接收到了，mq接收到回复后会删除消息；设置为false则需要手动确认
         */
        channel.basicConsume(Producer.QUEUE_NAME, true, consumer);

        //不关闭资源，应该一直监听消息
        //channel.close();
        //connection.close();
    }
}
