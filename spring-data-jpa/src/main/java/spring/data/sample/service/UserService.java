package spring.data.sample.service;

import java.util.List;

import spring.data.sample.entity.User;

public interface UserService {

	User save(User user);

	User getUser(String id);

	void delete(String id);

	int update(User user);

	List<User> list(String name);
}
