package com.evandrogoulart.aem.core.services.impl;

import com.evandrogoulart.aem.core.dtos.ProductDTO;
import com.evandrogoulart.aem.core.services.ProductService;
import com.evandrogoulart.aem.core.services.getResolverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Slf4j
@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService {
    private static final long serialVersionUID = 1L;
    private static final String RESOURCE_TYPE = "jump-shop/components/product";
    private static final String XPATH_QUERY = "//*[(@sling:resourceType='" + RESOURCE_TYPE + "')]";
    @Reference
    getResolverService getResolverResolver;
    @Override
    public void saveProduct(ProductDTO productDTO) throws PersistenceException {
        ResourceResolver resourceResolver = getResolverResolver.getResourceResolver();
        resourceResolver.findResources(XPATH_QUERY, "xpath").forEachRemaining(resource -> {
            resource.adaptTo(ModifiableValueMap.class).put("name", productDTO.getName());
            resource.adaptTo(ModifiableValueMap.class).put("category", productDTO.getCategory());
            resource.adaptTo(ModifiableValueMap.class).put("price", productDTO.getPrice());
            resource.adaptTo(ModifiableValueMap.class).put("fileReference", productDTO.getFileReference());
            if (resourceResolver.isLive() && resourceResolver.hasChanges()) {
                try {
                    resourceResolver.commit();
                } catch (PersistenceException e) {
                    log.error("Error trying to commit new ProductDTO data in ProductServiceImpl", e.getMessage());
                    throw new RuntimeException("Failed to commit product data, make sure resource matches the ProductDTO schema, is currently live and has changes to be commited.",e);
                }
            }
            resourceResolver.close();
        });
    }
}
