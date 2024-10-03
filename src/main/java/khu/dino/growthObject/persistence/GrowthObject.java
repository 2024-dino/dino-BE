package khu.dino.growthObject.persistence;

import jakarta.persistence.*;
import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "growthObject")
public class GrowthObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    private Category category;

    private String fileUrl;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "LEVEL1")
    private Step step;
}
