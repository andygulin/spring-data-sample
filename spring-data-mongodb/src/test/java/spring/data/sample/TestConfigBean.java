package spring.data.sample;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import spring.data.sample.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@ContextConfiguration(locations = {"classpath:applicationContext-mongodb-annotation.xml"})
public class TestConfigBean extends AbstractJUnit4SpringContextTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setName("aaaa");
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreateAt(new Date());
        this.mongoTemplate.save(user);
        System.out.println(user.getId());
    }

    @Test
    public void save2() {
        User user = new User();
        user.setName("aaa");
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreateAt(new Date());
        User currentUser = this.userService.save(user);
        System.out.println(currentUser.getId());
    }

    @Test
    public void grid() throws IOException {
        File dir = new File("D:/图片");
        File[] files = dir.listFiles();
        for (File file : files) {
            this.gridFsTemplate.store(FileUtils.openInputStream(file), file.getName());
            System.out.println("store -> " + file.getName());
        }
    }
}
