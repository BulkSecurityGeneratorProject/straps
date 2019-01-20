package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.InvoiceHeaderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvoiceHeader and its DTO InvoiceHeaderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoiceHeaderMapper extends EntityMapper<InvoiceHeaderDTO, InvoiceHeader> {



    default InvoiceHeader fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        invoiceHeader.setId(id);
        return invoiceHeader;
    }
}
