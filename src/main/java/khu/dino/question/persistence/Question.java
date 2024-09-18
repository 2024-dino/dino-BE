package khu.dino.question.persistence;

import jakarta.persistence.*;
import khu.dino.common.base.BaseEntity;
import lombok.*;

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


}
