package com.evandrogoulart.aem.core.dtos;

import org.apache.sling.api.resource.ModifiableValueMap;

public class ProductValueMapDTO extends ProductDTO {
    public void setValueMapWithProductDTO(ModifiableValueMap valueMap, ProductDTO productDTO) {
        valueMap.put("name", productDTO.getName());
        valueMap.put("category", productDTO.getCategory());
        valueMap.put("price", productDTO.getPrice());
        valueMap.put("fileReference", productDTO.getFileReference());
    }
    public void setValueMap(ModifiableValueMap valueMap) {
        valueMap.put("name", super.getName());
        valueMap.put("category", super.getCategory());
        valueMap.put("price", super.getPrice());
        valueMap.put("fileReference", super.getFileReference());
    }
}
