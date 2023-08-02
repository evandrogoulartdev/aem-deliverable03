package com.evandrogoulart.aem.core.dtos;

import lombok.*;
import org.apache.sling.api.resource.ModifiableValueMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NonNull
    private String name;
    @NonNull
    private String category;
    @NonNull
    private String price;
    @NonNull
    private String fileReference;
}
