package community.andela.com.AfricanBootcampGatewayService.accessibility.repository;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepositoryI extends JpaRepository<User, UUID> {
    User findUserByUserName(String username);
}
