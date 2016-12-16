package spring.data.sample;

import org.junit.Test;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.core.RedisCallback;

import java.util.List;

public class RedisListTest extends BaseTest {

    @Test
    public void lpush() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            String[] str = {"aa", "bb", "cc", "dd"};
            for (String s : str) {
                connection.lPush(key, serialize(s));
            }
            return null;
        });
    }

    @Test
    public void rpush() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            String[] str = {"11", "22", "33", "44"};
            for (String s : str) {
                connection.rPush(key, serialize(s));
            }
            return null;
        });
    }

    @Test
    public void lrange() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            List<byte[]> list = connection.lRange(key, 0L, 2L);
            for (byte[] b : list) {
                System.out.println(deserialize(b));
            }
            System.out.println();
            list = connection.lRange(key, 0L, -1L);
            for (byte[] b : list) {
                System.out.println(deserialize(b));
            }
            return null;
        });
    }

    @Test
    public void ltrim() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            connection.lTrim(key, 0L, 2L);
            return null;
        });
    }

    @Test
    public void lindex() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            byte[] value = connection.lIndex(key, 1L);
            System.out.println(deserialize(value));
            return null;
        });
    }

    @Test
    public void linsert() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            byte[] pivot = serialize("cc");
            byte[] value = serialize("hello");

            connection.lInsert(key, Position.BEFORE, pivot, value);

            pivot = serialize("cc");
            value = serialize("world");
            connection.lInsert(key, Position.AFTER, pivot, value);
            return null;
        });
    }

    @Test
    public void llen() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            long len = connection.lLen(key);
            System.out.println(len);
            return null;
        });
    }

    @Test
    public void lpop() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            byte[] value = connection.lPop(key);
            System.out.println(deserialize(value));
            return null;
        });
    }

    @Test
    public void rpop() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            byte[] value = connection.rPop(key);
            System.out.println(deserialize(value));
            return null;
        });
    }

    @Test
    public void lset() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("list");
            byte[] value = serialize("hello");
            connection.lSet(key, 2L, value);
            return null;
        });
    }
}
