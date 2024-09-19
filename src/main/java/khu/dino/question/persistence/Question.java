package khu.dino.question.persistence;

import jakarta.persistence.*;
import khu.dino.answer.persistence.Answer;
import khu.dino.common.base.BaseEntity;
import khu.dino.event.persistence.Event;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;

}
