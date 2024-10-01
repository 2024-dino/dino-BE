package khu.dino.question.persistence;

import jakarta.persistence.*;
import khu.dino.answer.persistence.Answer;
import khu.dino.common.base.BaseEntity;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne(mappedBy = "question")
    private Answer answer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;
    
    @Builder.Default
    private Boolean isPriority = false;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate occurredAt;

    @Builder.Default
    public Boolean isAnswered = false;


    public void updatePriorityStatus(Boolean isPriority) {
        this.isPriority = isPriority;
    }
}
