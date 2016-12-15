package spring.data.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import spring.data.sample.service.UserService;

@ContextConfiguration(locations = {"classpath:applicationContext-mongodb.xml"})
public class BaseTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected GridFsTemplate gridFsTemplate;
    @Autowired
    protected UserService userService;
}
