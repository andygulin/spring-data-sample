package spring.data.sample;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import spring.data.sample.bean.User;
import spring.data.sample.repositories.UserRepository;

@ContextConfiguration(locations = "classpath:spring-elasticsearch.xml")
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected ElasticsearchTemplate elasticsearchTemplate;

	protected String getId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	protected String getName() {
		return RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyz1234567890");
	}

	protected int getAge() {
		return RandomUtils.nextInt(100) + 10;
	}

	protected String getAddress() {
		final String[] addrs = { "shanghai", "beijing", "guangzhou", "shenzhen", "hunan", "henan", "xinjiang",
				"haerbing", "jinan", "nanjing" };
		return addrs[RandomUtils.nextInt(addrs.length - 1)];
	}

	protected Date getCreatedAt() {
		return DateUtils.addDays(new Date(), RandomUtils.nextInt(100) + 10);
	}

	protected void printQuery(QueryBuilder query) {
		Page<User> page = userRepository.search(query, new PageRequest(0, 10));
		System.out.println(page.getTotalElements());
		List<User> users = page.getContent();
		for (User user : users) {
			System.out.println(user);
		}
	}
}
