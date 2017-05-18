package spring.data.sample.service;

import spring.data.sample.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User getUser(String id);

    void delete(String id);

    int update(User user);

    List<User> list(String name);
}
