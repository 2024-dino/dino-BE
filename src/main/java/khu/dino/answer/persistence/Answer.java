package khu.dino.answer.persistence;

import jakarta.persistence.*;
import khu.dino.answer.persistence.enums.Type;
import khu.dino.common.base.BaseEntity;
import khu.dino.question.persistence.Question;
import lombok.*;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
@Builder
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String fileUrl;
    private String fileName;
}
