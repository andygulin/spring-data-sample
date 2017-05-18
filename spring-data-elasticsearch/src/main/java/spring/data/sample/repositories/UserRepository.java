package spring.data.sample.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.data.sample.bean.User;

public interface UserRepository extends ElasticsearchRepository<User, String> {

}
