package com.evandrogoulart.aem.core.services;

import com.evandrogoulart.aem.core.dtos.ProductDTO;
import org.apache.sling.api.resource.PersistenceException;

public interface ProductService {
    void saveProduct(ProductDTO productDTO) throws PersistenceException;
}
