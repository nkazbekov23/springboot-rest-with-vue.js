package letscode.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import letscode.sarafan.domain.Message;
import letscode.sarafan.domain.Views;
import letscode.sarafan.dto.EventType;
import letscode.sarafan.dto.ObjectType;
import letscode.sarafan.repo.MessageRepo;
import letscode.sarafan.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> sender;

    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender sender) {
        this.messageRepo = messageRepo;
        this.sender =  sender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setLocalDateTime(LocalDateTime.now());

        Message updatedMessage = messageRepo.save(message);
        sender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;

    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message)
    {
        //из message копируем в messageFromDB кроме id
        BeanUtils.copyProperties(message, messageFromDB, "id");
        Message updateMessage = messageRepo.save(messageFromDB);
        sender.accept(EventType.UPDATE, updateMessage);
        return updateMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
        sender.accept(EventType.REMOVE, message);
    }
}
