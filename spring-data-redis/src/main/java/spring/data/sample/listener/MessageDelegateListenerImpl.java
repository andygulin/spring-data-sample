package spring.data.sample.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class MessageDelegateListenerImpl implements MessageListener {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String p = redisTemplate.getStringSerializer().deserialize(pattern);
        if (p.equalsIgnoreCase("java")) {
            String channel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            System.out.println("channel = " + channel);
            System.out.println("body = " + body);
            System.out.println();
        }
    }
}
