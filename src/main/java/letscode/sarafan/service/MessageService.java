package letscode.sarafan.service;

import letscode.sarafan.domain.Message;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Nursultan Kazbekov
 */
public class MessageService {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }

}
