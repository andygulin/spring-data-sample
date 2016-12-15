package spring.data.sample;

import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import spring.data.sample.config.AppConfig;
import spring.data.sample.model.User;
import spring.data.sample.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@ContextConfiguration(classes = AppConfig.class)
public class SolrTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void build() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(UUID.randomUUID().toString());
        user.setAge(11);
        user.setAddress("shanghai");
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }

    @Test
    public void ping() {
        SolrPingResponse response = solrTemplate.ping();
        System.out.println(response.getStatus());
    }

    @Test
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    public void deleteById() {
        userRepository.delete("2");
    }

    @Test
    public void queryAll() {
        Pageable pageable = new SolrPageRequest(1, 10, Sort.Direction.DESC, "createdAt");
        Page<User> users = userRepository.findAll(pageable);
        print(users);
    }

    @Test
    public void queryOne() {
        User user = userRepository.findOne("2");
        System.out.println(user);
    }

    @Test
    public void queryByAge() {
        Pageable page = new SolrPageRequest(1, 10, Sort.Direction.DESC, "createdAt");
        Query query = new SimpleQuery("age:[1 TO 10]");
        query.setPageRequest(page);
        Page<User> users = solrTemplate.queryForPage(query, User.class);
        print(users);
    }

    @Test
    public void queryByName() {
        Pageable page = new SolrPageRequest(1, 10, Sort.Direction.DESC, "createdAt");
        Query query = new SimpleQuery("name:*aa*");
        query.setPageRequest(page);
        Page<User> users = solrTemplate.queryForPage(query, User.class);
        print(users);
    }

    @Test
    public void queryByNameAndAge() {
        Pageable page = new SolrPageRequest(1, 10, Sort.Direction.DESC, "createdAt");
        Query query = new SimpleQuery("name:*aa* AND age:[1 TO 10]");
        query.setPageRequest(page);
        Page<User> users = solrTemplate.queryForPage(query, User.class);
        print(users);
    }

    private void print(Page<User> users) {
        System.out.println("Number : " + users.getNumber());
        System.out.println("NumberOfElements : " + users.getNumberOfElements());
        System.out.println("Size : " + users.getSize());
        System.out.println("TotalElements : " + users.getTotalElements());
        System.out.println("TotalPages : " + users.getTotalPages());
        List<User> list = users.getContent();
        for (User user : list) {
            System.out.println(user);
        }
        System.out.println("Sort : " + users.getSort());
    }
}
