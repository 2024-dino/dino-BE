package khu.dino.growthObject.persistence.repository;


import khu.dino.growthObject.persistence.GrowthObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowthObjectRepository extends JpaRepository<GrowthObject, Long> {
}
