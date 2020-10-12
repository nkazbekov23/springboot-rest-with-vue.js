package letscode.sarafan.repo;

import letscode.sarafan.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nursultan Kazbekov
 */
public interface MessageRepo extends JpaRepository<Message, Long> {

}
