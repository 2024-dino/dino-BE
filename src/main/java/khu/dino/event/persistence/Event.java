package khu.dino.event.persistence;

import jakarta.persistence.*;
import khu.dino.common.base.BaseEntity;
import khu.dino.event.persistence.enums.Category;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.event.persistence.enums.Status;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
@Builder
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isDeleted;

    private int growth;

    @Enumerated(EnumType.STRING)
    private Status eventStatus;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

    @OneToMany(mappedBy = "event")
    private List<Question> questionList;

}
