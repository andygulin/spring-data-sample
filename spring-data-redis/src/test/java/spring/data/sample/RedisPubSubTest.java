package spring.data.sample;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

public class RedisPubSubTest extends BaseTest {

    @Test
    public void publish() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] channel = serialize("java");
                byte[] message = serialize("hello world");
                long clientSize = connection.publish(channel, message);
                System.out.println(clientSize);
                return null;
            }
        });
    }

    @Test
    public void subscribe() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] channel = serialize("java");
                connection.subscribe(messageListener, channel);
                return null;
            }
        });
    }

}
