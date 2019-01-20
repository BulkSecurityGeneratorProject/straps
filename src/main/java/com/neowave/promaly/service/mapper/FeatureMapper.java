package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.FeatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Feature and its DTO FeatureDTO.
 */
@Mapper(componentModel = "spring", uses = {LookupMapper.class})
public interface FeatureMapper extends EntityMapper<FeatureDTO, Feature> {

    @Mapping(source = "type.id", target = "typeId")
    FeatureDTO toDto(Feature feature);

    @Mapping(source = "typeId", target = "type")
    Feature toEntity(FeatureDTO featureDTO);

    default Feature fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feature feature = new Feature();
        feature.setId(id);
        return feature;
    }
}
