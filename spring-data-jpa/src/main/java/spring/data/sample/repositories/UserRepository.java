package spring.data.sample.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import spring.data.sample.entity.User;

import java.util.List;

public interface UserRepository extends
        PagingAndSortingRepository<User, String> {

    List<User> findByNameIsLike(String name);

    @Modifying
    @Query("update User set name=?2,age=?3,address=?4 where id=?1")
    int update(String id, String name, int age, String address);
}
