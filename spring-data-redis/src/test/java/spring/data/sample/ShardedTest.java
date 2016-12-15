package spring.data.sample;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.UUID;

@ContextConfiguration(locations = {"classpath:applicationContext-redis-sharded.xml"})
public class ShardedTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void set() {
        ShardedJedis jedis = shardedJedisPool.getResource();
        for (int i = 1; i <= 1000; i++) {
            jedis.set("user:" + i, UUID.randomUUID().toString().split("-")[0]);
        }
        shardedJedisPool.close();
    }

    @Test
    public void get() {
        ShardedJedis jedis = shardedJedisPool.getResource();
        String name = jedis.get("user:100");
        System.out.println(name);
        shardedJedisPool.close();
    }
}
