package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.InvoiceLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvoiceLine and its DTO InvoiceLineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoiceLineMapper extends EntityMapper<InvoiceLineDTO, InvoiceLine> {



    default InvoiceLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setId(id);
        return invoiceLine;
    }
}
