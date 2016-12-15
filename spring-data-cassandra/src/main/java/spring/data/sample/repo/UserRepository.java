package spring.data.sample.repo;

import org.springframework.data.repository.CrudRepository;

import spring.data.sample.bean.User;

public interface UserRepository extends CrudRepository<User, String> {

}
