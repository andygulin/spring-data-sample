package spring.data.sample;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.Serializable;

@ContextConfiguration(locations = {"classpath:applicationContext-redis-annotation.xml"})
public class TestConfigBean extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    @Autowired
    protected JdkSerializationRedisSerializer serialization;

    protected byte[] serialize(Object str) {
        return serialization.serialize(str);
    }

    protected Object deserialize(byte[] bytes) {
        return serialization.deserialize(bytes);
    }

    @Test
    public void set() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.set(serialize("name"), serialize("andy"));
            return null;
        });
    }

    @Test
    public void get(){
        Object result = redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] name = connection.get(serialize("name"));
            return deserialize(name);
        });
        System.out.println(result);
    }
}
