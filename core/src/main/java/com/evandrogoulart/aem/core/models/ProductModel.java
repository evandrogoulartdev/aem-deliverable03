package com.evandrogoulart.aem.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Getter
@Model(adaptables = Resource.class)
public class ProductModel {
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values = "NVIDIA RTX 3070")
    private String name;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values = "Graphics Card")
    private String category;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values = "R$ 2000,99")
    private String price;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values = "/content/dam/jump-shop/gpu_small.png")
    private String fileReference;
}
