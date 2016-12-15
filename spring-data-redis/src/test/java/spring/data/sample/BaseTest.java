package spring.data.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.Serializable;

@ContextConfiguration(locations = {"classpath:applicationContext-redis.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    @Autowired
    protected JdkSerializationRedisSerializer serialization;

    @Autowired
    protected MessageListener messageListener;

    protected byte[] serialize(Object str) {
        return serialization.serialize(str);
    }

    protected Object deserialize(byte[] bytes) {
        return serialization.deserialize(bytes);
    }
}
