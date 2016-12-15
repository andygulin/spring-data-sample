package spring.data.sample.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import spring.data.sample.model.User;

public interface UserRepository extends SolrCrudRepository<User, String> {

}
