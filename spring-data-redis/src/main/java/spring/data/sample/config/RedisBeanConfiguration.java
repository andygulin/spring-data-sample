package spring.data.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import spring.data.sample.listener.MessageDelegateListenerImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RedisBeanConfiguration {

    private
    @Value("${redis.hostName}")
    String hostName;
    private
    @Value("${redis.port}")
    int port;
    private
    @Value("${redis.password}")
    String password;
    private
    @Value("${redis.maxTotal}")
    int maxTotal;
    private
    @Value("${redis.maxIdle}")
    int maxIdle;
    private
    @Value("${redis.minIdle}")
    int minIdle;
    private
    @Value("${redis.testOnBorrow}")
    boolean testOnBorrow;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(hostName);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        connectionFactory.setPoolConfig(jedisPoolConfig());
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate() {
        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public JdkSerializationRedisSerializer serialization() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    public MessageListener messageDelegateListener() {
        return new MessageDelegateListenerImpl();
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        MessageListenerAdapter adapter = new MessageListenerAdapter();
        adapter.setSerializer(serialization());
        adapter.setDelegate(messageDelegateListener());
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());

        Map<MessageListener, Collection<Topic>> messageListeners = new HashMap<MessageListener, Collection<Topic>>();
        List<Topic> listeners = new ArrayList<Topic>();
        listeners.add(new ChannelTopic("java"));
        messageListeners.put(messageListener(), listeners);
        // container.setMessageListeners(messageListeners);
        return container;
    }
}
