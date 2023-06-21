package com.evandrogoulart.aem.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class)
public class ProductModel {
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String text;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String fileReference;

    public String getText() {
        return text;
    }
    public String getFileReference() {
        return fileReference;
    }
}
