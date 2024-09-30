package khu.dino.growthObject.persistence;

import jakarta.persistence.*;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.growthObject.persistence.enums.Category;
import lombok.*;

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
}
