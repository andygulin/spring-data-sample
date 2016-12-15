package spring.data.sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.data.sample.User;

public interface UserRepository extends MongoRepository<User, String> {

}
