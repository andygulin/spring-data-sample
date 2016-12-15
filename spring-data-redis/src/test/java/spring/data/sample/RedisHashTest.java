package spring.data.sample;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RedisHashTest extends BaseTest {

    @Test
    public void hset() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                byte[] field = serialize("name");
                byte[] value = serialize("andy");
                boolean success = connection.hSet(key, field, value);
                System.out.println(success);
                return null;
            }
        });
    }

    @Test
    public void hmset() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                Map<byte[], byte[]> hashes = new HashMap<>();
                hashes.put(serialize("name"), serialize("andy"));
                hashes.put(serialize("age"), serialize("11"));
                hashes.put(serialize("address"), serialize("shanghai"));
                connection.hMSet(key, hashes);
                return null;
            }
        });
    }

    @Test
    public void hget() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                byte[] field = serialize("name");
                byte[] value = connection.hGet(key, field);
                System.out.println(deserialize(value));
                return null;
            }
        });
    }

    @Test
    public void hmget() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                byte[] field1 = serialize("name");
                byte[] field2 = serialize("age");
                List<byte[]> values = connection.hMGet(key, field1, field2);
                for (byte[] value : values) {
                    System.out.println(deserialize(value));
                }
                return null;
            }
        });
    }

    @Test
    public void hkeys() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                Set<byte[]> keys = connection.hKeys(key);
                for (byte[] k : keys) {
                    System.out.println(deserialize(k));
                }
                return null;
            }
        });
    }

    @Test
    public void hvals() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                List<byte[]> keys = connection.hVals(key);
                for (byte[] k : keys) {
                    System.out.println(deserialize(k));
                }
                return null;
            }
        });
    }

    @Test
    public void hgetall() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                Map<byte[], byte[]> all = connection.hGetAll(key);
                for (Entry<byte[], byte[]> entry : all.entrySet()) {
                    Object k = deserialize(entry.getKey());
                    Object v = deserialize(entry.getValue());
                    System.out.println(k + " = " + v);
                }
                return null;
            }
        });
    }

    @Test
    public void hdel() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("user");
                byte[] field1 = serialize("name");
                byte[] field2 = serialize("age");
                long size = connection.hDel(key, field1, field2);
                System.out.println(size);
                return null;
            }
        });
    }
}
