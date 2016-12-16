package spring.data.sample;

import org.junit.Test;
import org.springframework.data.redis.core.RedisCallback;

import java.util.List;
import java.util.Set;

public class RedisSetTest extends BaseTest {

    @Test
    public void sadd() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("set");
            String[] str = {"aa", "bb", "cc", "dd"};
            for (String s : str) {
                connection.sAdd(key, serialize(s));
            }
            return null;
        });
    }

    @Test
    public void smembers() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("set");
            Set<byte[]> values = connection.sMembers(key);
            for (byte[] value : values) {
                System.out.println(deserialize(value));
            }
            return null;
        });
    }

    @Test
    public void srandommembers() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("set");
            byte[] value = connection.sRandMember(key);
            List<byte[]> values = connection.sRandMember(key, 2L);
            System.out.println(deserialize(value));
            System.out.println();
            for (byte[] v : values) {
                System.out.println(deserialize(v));
            }
            return null;
        });
    }

    @Test
    public void spop() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("set");
            byte[] value = connection.sPop(key);
            System.out.println(deserialize(value));
            return null;
        });
    }

    @Test
    public void srem() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key = serialize("set");
            byte[] v1 = serialize("11");
            byte[] v2 = serialize("22");
            long size = connection.sRem(key, v1, v2);
            System.out.println(size);
            return null;
        });
    }

    @Test
    public void smove() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] srcKey = serialize("set");
            byte[] destKey = serialize("set2");
            byte[] value = serialize("33");
            boolean result = connection.sMove(srcKey, destKey, value);
            System.out.println(result);
            return null;
        });
    }

    @Test
    public void sdiff() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            Set<byte[]> diff = connection.sDiff(key1, key2);
            for (byte[] b : diff) {
                System.out.println(deserialize(b));
            }
            return null;
        });
    }

    @Test
    public void sdiffstore() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] diffKey = serialize("diffset");
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            long size = connection.sDiffStore(diffKey, key1, key2);
            System.out.println(size);
            return null;
        });
    }

    @Test
    public void sinter() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            Set<byte[]> inter = connection.sInter(key1, key2);
            for (byte[] b : inter) {
                System.out.println(deserialize(b));
            }
            return null;
        });
    }

    @Test
    public void sinterstore() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] interKey = serialize("interset");
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            long size = connection.sInterStore(interKey, key1, key2);
            System.out.println(size);
            return null;
        });
    }

    @Test
    public void sunion() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            Set<byte[]> union = connection.sUnion(key1, key2);
            for (byte[] b : union) {
                System.out.println(deserialize(b));
            }
            return null;
        });
    }

    @Test
    public void sunionstore() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] unionKey = serialize("unionset");
            byte[] key1 = serialize("set");
            byte[] key2 = serialize("set2");
            long size = connection.sUnionStore(unionKey, key1, key2);
            System.out.println(size);
            return null;
        });
    }

}
