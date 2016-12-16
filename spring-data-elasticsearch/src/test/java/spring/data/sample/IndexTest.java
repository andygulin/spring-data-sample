package spring.data.sample;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;
import spring.data.sample.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexTest extends BaseTest {

	@Repeat(100)
	@Test
	public void index() {
		final int INDEX_COUNT = 10000;
		List<User> users = new ArrayList<>(INDEX_COUNT);
		for (int i = 0; i < INDEX_COUNT; i++) {
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
	public void count() {
		System.out.println(userRepository.count());
	}

	@Test
	public void searchByAge() {
		QueryBuilder query = QueryBuilders.termQuery("age", 11);
		printQuery(query);
	}

	@Test
	public void searchRangeByAge() {
		QueryBuilder query = QueryBuilders.rangeQuery("age").from(10).to(15).includeLower(true).includeUpper(true);
		printQuery(query);
	}

	@Test
	public void searchLikeByName() {
		QueryBuilder query = QueryBuilders.wildcardQuery("name", "*abc*");
		printQuery(query);
	}

	@Test
	public void searchPrefixByName() {
		QueryBuilder query = QueryBuilders.prefixQuery("name", "38");
		printQuery(query);
	}

	@Test
	public void searchInByAddress() {
		QueryBuilder query = QueryBuilders.termsQuery("address", Arrays.asList("shanghai", "beijing"));
		printQuery(query);
	}
}