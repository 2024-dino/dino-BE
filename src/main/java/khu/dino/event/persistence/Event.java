package khu.dino.event.persistence;

import jakarta.persistence.*;
import khu.dino.common.base.BaseEntity;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.event.persistence.enums.Status;
import khu.dino.growthObject.persistence.GrowthObject;
import khu.dino.growthObject.persistence.enums.Category;
import khu.dino.member.persistence.Member;
import khu.dino.question.persistence.Question;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime occurrenceTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Step step;

    @Enumerated(EnumType.STRING)
    private Status eventStatus;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @OneToOne
    @JoinColumn(name = "representative_question_id")
    private Question representativeQuestion;

    @OneToOne
    @JoinColumn(name =  "growth_object_id")
    private GrowthObject growthObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

    @OneToMany(mappedBy = "event")
    private List<Question> questionList;

}
