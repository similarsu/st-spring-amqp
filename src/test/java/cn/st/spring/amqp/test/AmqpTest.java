package cn.st.spring.amqp.test;

import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>
 * description:amqp 测试类
 * </p>
 * 
 * @author coolearth
 * @since 2015年5月4日
 * @version v1.0
 */
public class AmqpTest {
    @Test
    public void testSend() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("192.168.44.131");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setVirtualHost("first-vhost");
        cachingConnectionFactory.setUsername("send");
        cachingConnectionFactory.setPassword("send");
        Connection connection = cachingConnectionFactory.createConnection();
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory);
        Queue queue = new Queue("spring.amqp");
        rabbitAdmin.declareQueue(queue);
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(cachingConnectionFactory);
        // rabbitTemplate.convertAndSend("spring.amqp", "哈哈");
        // rabbitTemplate.send("spring.amqp", MessageBuilder.withBody("哈哈".getBytes()).build());
        rabbitTemplate.send("spring.amqp", new Message("哈哈".getBytes(), MessagePropertiesBuilder
                .newInstance().build()));
        System.out.println(new String(rabbitTemplate.receive("spring.amqp").getBody()));
        connection.close();
    }

    @Test
    public void testReceive() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("192.168.44.131");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setVirtualHost("first-vhost");
        cachingConnectionFactory.setUsername("receive");
        cachingConnectionFactory.setPassword("receive");
        Connection connection = cachingConnectionFactory.createConnection();
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory);
        Queue queue = new Queue("spring.amqp");
        rabbitAdmin.declareQueue(queue);
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(cachingConnectionFactory);
        Message message = rabbitTemplate.receive("spring.amqp");
        System.out.println(new String(message.getBody()));
        connection.close();
    }
}
