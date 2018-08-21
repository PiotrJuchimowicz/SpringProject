package comcompany.app.base.Models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

//Message entity is owner  side between Employees(sender&recipient)
@Entity(name = "Message")
@Table(name = "MESSAGE")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipient", "sender"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject, content;
    private LocalDateTime creationDate;
    @Enumerated(value = EnumType.STRING)
    private MessageStatus messageStatus;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "recipient_id")
    private Employee recipient;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sender_id")
    private Employee sender;

    @Override
    public String toString() {
        return "Message{" +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", messageStatus=" + messageStatus +
                ", recipient=" + recipient.getName() + " " + recipient.getSurname() +
                ", sender=" + sender.getName() + " " + sender.getSurname() +
                '}';
    }

}
