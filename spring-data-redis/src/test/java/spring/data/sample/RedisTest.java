package spring.data.sample;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultSortParameters;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.connection.SortParameters.Range;
import org.springframework.data.redis.core.RedisCallback;

import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class RedisTest extends BaseTest {

    @Test
    public void flushdb() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(1);
                connection.flushDb();
                return null;
            }
        });
    }

    @Test
    public void flushall() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }

    @Test
    public void info() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Properties infos = connection.info();
                Set<Entry<Object, Object>> iter = infos.entrySet();
                for (Entry<Object, Object> entry : iter) {
                    System.out.println(entry.getKey() + " = "
                            + entry.getValue());
                }
                return null;
            }
        });
    }

    @Test
    public void keys() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(0);
                byte[] pattern = serialize("*");
                Set<byte[]> keys = connection.keys(pattern);
                for (byte[] key : keys) {
                    System.out.println(deserialize(key));
                }
                return null;
            }
        });
    }

    @Test
    public void sort() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = serialize("list");
                List<byte[]> values = connection.sort(key,
                        new DefaultSortParameters(new Range(0, -1), Order.DESC,
                                true));
                for (byte[] value : values) {
                    System.out.println(deserialize(value));
                }
                return null;
            }
        });
    }
}
