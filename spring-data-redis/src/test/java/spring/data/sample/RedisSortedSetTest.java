package spring.data.sample;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;

import java.util.HashSet;
import java.util.Set;

public class RedisSortedSetTest extends BaseTest {

    @Test
    public void zadd() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                Set<Tuple> tuples = new HashSet<>();
                tuples.add(new DefaultTuple(serialize("aa"), 1d));
                tuples.add(new DefaultTuple(serialize("bb"), 2d));
                tuples.add(new DefaultTuple(serialize("cc"), 4d));
                tuples.add(new DefaultTuple(serialize("dd"), 3d));
                long size = connection.zAdd(key, tuples);
                System.out.println(size);
                return null;
            }
        });
    }

    @Test
    public void zrange() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                Set<byte[]> values = connection.zRange(key, 0L, -1L);
                for (byte[] value : values) {
                    System.out.println(deserialize(value));
                }
                System.out.println();
                Set<Tuple> tuples = connection.zRangeWithScores(key, 0L, -1L);
                for (Tuple tuple : tuples) {
                    System.out.println(deserialize(tuple.getValue()) + " = " + tuple.getScore());
                }
                System.out.println();
                values = connection.zRangeByScore(key, 2L, 3L);
                for (byte[] value : values) {
                    System.out.println(deserialize(value));
                }
                return null;
            }
        });
    }

    @Test
    public void zrank() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                byte[] value = serialize("bb");
                long idx = connection.zRank(key, value);
                System.out.println(idx);
                return null;
            }
        });
    }

    @Test
    public void zcard() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                long size = connection.zCard(key);
                System.out.println(size);
                return null;
            }
        });
    }

    @Test
    public void zcount() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                long size = connection.zCount(key, 2L, 3L);
                System.out.println(size);
                return null;
            }
        });
    }

    @Test
    public void zrem() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                byte[] v1 = serialize("11");
                byte[] v2 = serialize("22");
                long size = connection.zRem(key, v1, v2);
                System.out.println(size);
                System.out.println();
                size = connection.zRemRange(key, 2L, 4L);
                System.out.println(size);
                System.out.println();
                size = connection.zRemRangeByScore(key, 10d, 10d);
                System.out.println(size);
                return null;
            }
        });
    }

    @Test
    public void zrevrange() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = serialize("zset");
                Set<byte[]> values = connection.zRevRange(key, 0L, 2L);
                for (byte[] value : values) {
                    System.out.println(deserialize(value));
                }
                return null;
            }
        });
    }
}
