package khu.dino.growthObject.implement;


import khu.dino.common.annotation.Adapter;
import khu.dino.growthObject.persistence.repository.GrowthObjectRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class GrowthObjectQueryAdapter {

    private final GrowthObjectRepository growthObjectRepository;


}
