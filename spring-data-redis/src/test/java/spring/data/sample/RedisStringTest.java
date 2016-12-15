package spring.data.sample;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisStringTest extends BaseTest {

    @Test
    public void set() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(serialize("name"), serialize("andy"));
                return null;
            }
        });
    }

    @Test
    public void mset() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> tuple = new HashMap<>();
                tuple.put(serialize("age"), serialize("11"));
                tuple.put(serialize("address"), serialize("shanghai"));
                connection.mSet(tuple);
                return null;
            }
        });
    }

    @Test
    public void mget() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key1 = serialize("age");
                byte[] key2 = serialize("name");
                byte[] key3 = serialize("address");
                List<byte[]> values = connection.mGet(key1, key2, key3);
                for (byte[] value : values) {
                    Object val = deserialize(value);
                    System.out.println(val);
                }
                return null;
            }
        });
    }

    @Test
    public void get() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                byte[] value = connection.get(key);
                Object name = deserialize(value);
                System.out.println(name);
                return null;
            }
        });
    }

    @Test
    public void append() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                byte[] value = serialize("gulin");
                long size = connection.append(key, value);
                System.out.println(size);
                return null;
            }
        });
    }

    @Test
    public void incr() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("age");
                long value = connection.incr(key);
                System.out.println(value);

                value = connection.incrBy(key, 2L);
                System.out.println(value);
                return null;

            }
        });
    }

    @Test
    public void decr() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("age");
                long value = connection.decr(key);
                System.out.println(value);

                value = connection.decrBy(key, 2L);
                System.out.println(value);
                return null;

            }
        });
    }

    @Test
    public void getrange() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                byte[] value = connection.getRange(key, 0L, 3L);
                Object val = deserialize(value);
                System.out.println(val);
                return null;

            }
        });
    }

    @Test
    public void setrange() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                byte[] value = serialize("@hotmail.com");
                connection.setRange(key, value, 9L);
                return null;

            }
        });
    }

    @Test
    public void strlen() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                long len = connection.strLen(key);
                System.out.println(len);
                return null;

            }
        });
    }

    @Test
    public void delete() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("name");
                connection.del(key);
                return null;
            }
        });
    }

}
