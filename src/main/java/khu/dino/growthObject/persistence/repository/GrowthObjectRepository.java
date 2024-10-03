package khu.dino.growthObject.persistence.repository;


import khu.dino.common.enums.Step;
import khu.dino.event.persistence.enums.Emotion;
import khu.dino.growthObject.persistence.GrowthObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrowthObjectRepository extends JpaRepository<GrowthObject, Long> {

    List<GrowthObject> findAllByStepAndEmotion(Step step, Emotion emotion);

    List<GrowthObject> findAllByStepAndEmotionAndFileNameStartingWith(Step step, Emotion emotion, String fileName);
}
