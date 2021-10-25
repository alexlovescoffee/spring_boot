package my.spring.spring_boot.repository;

import my.spring.spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u JOIN FETCH u.roles where u.email=?1")
    User findByEmail(String email);
    User findByEmailAndIdNot(String name, long exclude);
}
