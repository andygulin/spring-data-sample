package spring.data.sample;

import com.mongodb.WriteResult;
import org.junit.Test;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MongoTest extends BaseTest {

    @Test
    public void repositoryTest() {
        User user = new User();
        user.setName("aaa");
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreateAt(new Date());
        User currentUser = this.userService.save(user);
        System.out.println(currentUser);
    }

    @Test
    public void save() {
        User user = new User();
        user.setName("aaa");
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreateAt(new Date());
        this.mongoTemplate.save(user);
        System.out.println(user.getId());
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("aaa");
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreateAt(new Date());
        this.mongoTemplate.insert(user);
        System.out.println(user.getId());
    }

    @Test
    public void batchInsert() {
        List<User> users = new ArrayList<>(2);
        users.add(new User("aaa", 11, "shanghai", new Date()));
        users.add(new User("bbb", 12, "beijing", new Date()));
        this.mongoTemplate.insert(users, User.class);
        for (User user : users) {
            System.out.println(user.getId());
        }
    }

    @Test
    public void count() {
        long count = this.mongoTemplate.count(new Query(), User.class);
        System.out.println(count);
        count = this.mongoTemplate.count(new Query(Criteria.where("name").is("aaa")), User.class);
        System.out.println(count);
    }

    @Test
    public void remove() {
        User user = this.mongoTemplate.findOne(new Query(Criteria.where("name").is("aaa")), User.class);
        WriteResult result = this.mongoTemplate.remove(user);
        System.out.println(result.getN());
        result = this.mongoTemplate.remove(new Query(Criteria.where("name").is("bbb")), User.class);
        System.out.println(result.getN());
    }

    @Test
    public void findAndRemove() {
        this.mongoTemplate.findAndRemove(new Query(Criteria.where("name").is("aaa")), User.class);
    }

    @Test
    public void findAll() {
        List<User> users = this.mongoTemplate.findAll(User.class);
        System.out.println(users);
    }

    @Test
    public void findAndModify() {
        this.mongoTemplate.findAndModify(new Query(Criteria.where("name").is("aaa")), new Update().set("name", "aaaaa"),
                User.class);
    }

    @Test
    public void findAndModifyOption() {
        User user = this.mongoTemplate.findAndModify(new Query(Criteria.where("name").is("bbb")),
                new Update().set("name", "bbbbbb"), FindAndModifyOptions.options().returnNew(true), User.class);
        System.out.println(user);
    }

    @Test
    public void updateFirst() {
        // 更新第一条
        WriteResult result = this.mongoTemplate.updateFirst(new Query(Criteria.where("name").is("aaa")),
                new Update().set("name", "aaaaa"), User.class);
        System.out.println(result.getN());
    }

    @Test
    public void updateMulti() {
        // 更新多条数据
        WriteResult result = this.mongoTemplate.updateMulti(new Query(Criteria.where("name").is("aaa")),
                new Update().set("name", "aaaaa"), User.class);
        System.out.println(result.getN());
    }

    @Test
    public void upsert() {
        // 找到就更新第一条，找不到就插入
        WriteResult result = this.mongoTemplate.upsert(new Query(Criteria.where("name").is("aaa")),
                new Update().set("name", "aaaaa"), User.class);
        System.out.println(result.getN());
    }

    @Test
    public void indexOps() {
        IndexOperations indexOperations = this.mongoTemplate.indexOps(User.class);
        List<IndexInfo> indexInfos = indexOperations.getIndexInfo();
        for (IndexInfo indexInfo : indexInfos) {
            System.out.println("indexName:" + indexInfo.getName());
            System.out.println("unique:" + indexInfo.isUnique());
            System.out.println("dropDuplicates:" + indexInfo.isDropDuplicates());
            System.out.println("sparse:" + indexInfo.isSparse());
            List<IndexField> indexFields = indexInfo.getIndexFields();
            for (IndexField indexField : indexFields) {
                System.out.println("field:" + indexField.getKey());
            }
            System.out.println();
        }
    }

    @Test
    public void geoNear() {
        List<Map> maps = new ArrayList<>(8);
        maps.add(new Map(new double[]{10d, 20d}));
        maps.add(new Map(new double[]{20d, 20d}));
        maps.add(new Map(new double[]{30d, 30d}));
        maps.add(new Map(new double[]{40d, 50d}));
        maps.add(new Map(new double[]{50d, 60d}));
        maps.add(new Map(new double[]{30d, 40d}));
        maps.add(new Map(new double[]{70d, 80d}));
        maps.add(new Map(new double[]{90d, 10d}));
        this.mongoTemplate.insertAll(maps);

        GeoResults<Map> geoResults = this.mongoTemplate.geoNear(NearQuery.near(new Point(10d, 20d)).maxDistance(20d),
                Map.class);
        List<GeoResult<Map>> results = geoResults.getContent();
        for (GeoResult<Map> geoResult : results) {
            Map content = geoResult.getContent();
            System.out.println(content.getId());
            System.out.println(Arrays.toString(content.getPosition()));
            System.out.println(geoResult.getDistance().getValue());
            System.out.println();
        }
    }

    @Test
    public void group() {
        String init = "{ count : 0 }";
        String reduce = "function( doc , prev ) { prev.count += 1 }";
        GroupByResults<ResultBean> results = this.mongoTemplate.group("user",
                new GroupBy("name").initialDocument(init).reduceFunction(reduce), ResultBean.class);
        for (ResultBean bean : results) {
            System.out.println(bean.getName() + " " + bean.getCount());
        }
    }
}
