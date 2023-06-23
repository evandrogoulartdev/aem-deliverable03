package com.evandrogoulart.aem.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Getter
@Model(adaptables = Resource.class)
public class ProductModel {

    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    private String name;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    private String category;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    private String price;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    private String fileReference;
    @SlingObject
    private Resource currentResource;

}
