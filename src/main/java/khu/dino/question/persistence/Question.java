package khu.dino.question.persistence;

import jakarta.persistence.*;
import khu.dino.answer.persistence.Answer;
import khu.dino.common.base.BaseEntity;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import lombok.*;

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

    private Boolean isPriority;
    private LocalDate occurredAt;

}
