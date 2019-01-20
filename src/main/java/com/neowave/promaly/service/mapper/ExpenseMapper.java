package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.ExpenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Expense and its DTO ExpenseDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, BuildingMapper.class, PropertyUnitMapper.class, LookupMapper.class})
public interface ExpenseMapper extends EntityMapper<ExpenseDTO, Expense> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    @Mapping(source = "type.id", target = "typeId")
    ExpenseDTO toDto(Expense expense);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    @Mapping(source = "typeId", target = "type")
    Expense toEntity(ExpenseDTO expenseDTO);

    default Expense fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expense expense = new Expense();
        expense.setId(id);
        return expense;
    }
}
