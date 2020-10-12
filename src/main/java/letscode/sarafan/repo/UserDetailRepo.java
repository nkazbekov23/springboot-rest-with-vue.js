package letscode.sarafan.repo;

import letscode.sarafan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nursultan Kazbekov
 */
public interface UserDetailRepo extends JpaRepository<User, String> {

}
