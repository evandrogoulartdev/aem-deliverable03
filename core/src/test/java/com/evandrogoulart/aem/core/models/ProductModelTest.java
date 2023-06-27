package com.evandrogoulart.aem.core.models;

import com.evandrogoulart.aem.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class ProductModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(ProductModel.class);
        context.load().json("/com/evandrogoulart/aem/core/models/product/ProductModelTest.json","/content");
    }

    @Test
    void getName() {
        final String expected = "name";

        final ProductModel productModel = context.resourceResolver().getResource("/content/product").adaptTo(ProductModel.class);

        final String actual = productModel.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getCategory() {
        final String expected = "category";

        final ProductModel productModel = context.resourceResolver().getResource("/content/product").adaptTo(ProductModel.class);

        final String actual = productModel.getCategory();

        assertEquals(expected, actual);
    }

    @Test
    void getPrice() {
        final String expected = "price";

        final ProductModel productModel = context.resourceResolver().getResource("/content/product").adaptTo(ProductModel.class);

        final String actual = productModel.getPrice();

        assertEquals(expected, actual);
    }

    @Test
    void getFileReference() {
        final String expected = "fileReference";

        final ProductModel productModel = context.resourceResolver().getResource("/content/product").adaptTo(ProductModel.class);

        final String actual = productModel.getFileReference();

        assertEquals(expected, actual);
    }

}