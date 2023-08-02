package com.evandrogoulart.aem.core.services.impl;

import com.evandrogoulart.aem.core.dtos.ProductDTO;
import com.evandrogoulart.aem.core.dtos.ProductValueMapDTO;
import com.evandrogoulart.aem.core.services.ProductService;
import com.evandrogoulart.aem.core.services.ResourceResolverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Slf4j
@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService {
    private static final String RESOURCE_TYPE = "jump-shop/components/product";
    private static final String XPATH_QUERY = "//*[(@sling:resourceType='" + RESOURCE_TYPE + "')]";
    @Reference
    private ResourceResolverService resourceResolverService;
    @Override
    public void saveProduct(ProductDTO productDTO) throws PersistenceException {
        ResourceResolver resolver = resourceResolverService.getResourceResolver();
        ProductValueMapDTO productValueMapDTO = new ProductValueMapDTO();

        resolver.findResources(XPATH_QUERY, "xpath").forEachRemaining(resource -> {
            productValueMapDTO.setValueMapWithProductDTO(resource.adaptTo(ModifiableValueMap.class), productDTO);
            if (resolver.isLive() && resolver.hasChanges()) {
                try {
                    resolver.commit();
                    log.info("Saved new following productDTO successfully: {}", productDTO.getName());
                } catch (PersistenceException e) {
                    log.error("Failed to commit new productDTO resource: ", e.getMessage());
                    throw new RuntimeException("Failed to commit productDTO resource, make sure your resource matches the ProductDTO schema, is currently live and has changes to be committed.", e);
                }
            }
            resolver.close();
        });
    }
}
