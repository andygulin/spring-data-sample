package spring.data.sample;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.junit.Test;
import org.springframework.data.cassandra.core.WriteListener;
import org.springframework.test.annotation.Repeat;
import org.springframework.util.CollectionUtils;
import spring.data.sample.bean.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CassandraTest extends BaseTest {

    @Repeat(10)
    @Test
    public void save() {
        final int BATCH_SIZE = 1000;
        List<User> users = new ArrayList<>(BATCH_SIZE);
        for (int i = 1; i <= BATCH_SIZE; i++) {
            User user = new User();
            user.setId(getId());
            user.setName(getName());
            user.setAge(getAge());
            user.setAddress(getAddress());
            user.setCreatedAt(getCreatedAt());
            users.add(user);
        }
        userRepository.save(users);
    }

    @Test
    public void asyncSave() {
        User user = new User();
        user.setId(getId());
        user.setName(getName());
        user.setAge(getAge());
        user.setAddress(getAddress());
        user.setCreatedAt(getCreatedAt());
        cassandraTemplate.insertAsynchronously(user, new WriteListener<User>() {
            @Override
            public void onWriteComplete(Collection<User> entities) {
                System.out.println(entities);
            }

            @Override
            public void onException(Exception x) {
                x.printStackTrace();
            }
        });
    }

    @Test
    public void count() {
        System.out.println(userRepository.count());
    }

    @Test
    public void query() {
        Iterator<User> iter = userRepository.findAll().iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            System.out.println(user);
        }
    }

    @Test
    public void selectByAge() {
        List<User> users = cassandraTemplate.select("SELECT * FROM " + TABLE_NAME + " WHERE age = 10", User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void selectBuilderByAge() {
        Select select = QueryBuilder.select().from(TABLE_NAME);
        select.where(QueryBuilder.eq("age", 16));
        List<User> users = cassandraTemplate.select(select, User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void selectByAgeRange() {
        Select select = QueryBuilder.select().from(TABLE_NAME);
        select.where(QueryBuilder.gte("age", 10)).and(QueryBuilder.lte("age", 15));
        List<User> users = cassandraTemplate.select(select, User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void selectByLimit() {
        Select select = QueryBuilder.select().from(TABLE_NAME);
        select.where().limit(10);
        List<User> users = cassandraTemplate.select(select, User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void delete() {
        User user = cassandraTemplate.selectOne("SELECT * FROM " + TABLE_NAME + " WHERE name = 'aaaa'", User.class);
        System.out.println(user);
        if (user != null)
            cassandraTemplate.delete(user);
    }

    @Test
    public void update() {
        List<User> users = cassandraTemplate.select("SELECT * FROM " + TABLE_NAME + " WHERE age = 11", User.class);
        System.out.println(users.size());
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                user.setAge(111);
            }
            cassandraTemplate.update(users);
        }
    }

    @Test
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
