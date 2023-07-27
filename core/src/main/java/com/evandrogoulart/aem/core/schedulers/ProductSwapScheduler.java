package com.evandrogoulart.aem.core.schedulers;

import com.evandrogoulart.aem.core.configs.ProductSwapConfiguration;
import com.evandrogoulart.aem.core.dtos.ProductDTO;
import com.evandrogoulart.aem.core.entities.Product;
import com.evandrogoulart.aem.core.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

import java.util.Arrays;
import java.util.List;

import static com.evandrogoulart.aem.core.schedulers.ProductSwapScheduler.NAME;

@Slf4j
@Component(
        immediate = true,
        service = Runnable.class,
        property = {
                Constants.SERVICE_ID + " = " + NAME
        }
)
@Designate(ocd = ProductSwapConfiguration.class)
public class ProductSwapScheduler implements Runnable {
    protected static final String NAME = "Product Swap Scheduler";
    private static final String TAG = ProductSwapScheduler.class.getSimpleName();
    @Reference
    private Scheduler scheduler;
    @Reference
    private ProductService productService;
    private String schedulerName;
    private List<ProductDTO> productList;
    private int currentListIndex = 0;
    @Activate
    protected void activate(ProductSwapConfiguration configuration) {
        log.debug("{}: initializing properties for scheduler", TAG);
        this.schedulerName = configuration.schedulerName();
        log.debug("{}: name of the scheduler: {}", TAG, schedulerName);

        productList = Arrays.asList(
                new ProductDTO("MSI Z490 TOMAHAWK", "Motherboard", "BRL 1399.99", "/content/dam/training-sling/asset.jpg"),
                new ProductDTO("Intel i9 12900K", "Processor", "BRL 2599.99", "/content/dam/training-sling/asset.jpg"),
                new ProductDTO("NVIDIA RTX 3070", "Graphics Card", "BRL 2000.99", "/content/dam/training-sling/asset.jpg"));

    }

    @Modified
    protected void modified(ProductSwapConfiguration configuration) {
        log.info("{}: modifying scheduler with name: {}", TAG, schedulerName);
        removeScheduler(configuration);
        addScheduler(configuration);
    }

    @Deactivate
    protected void deactivate(ProductSwapConfiguration configuration) {
        log.debug("{}: removing scheduler: {}", TAG, schedulerName);
        removeScheduler(configuration);
    }

    private void addScheduler(ProductSwapConfiguration configuration) {
        if (configuration.enabled()) {
            log.info("{}: scheduler: {} is enabled", TAG, schedulerName);
            ScheduleOptions scheduleOptions = scheduler.EXPR(configuration.cronExpression());
            scheduleOptions.name(schedulerName);
            scheduleOptions.canRunConcurrently(false);
            scheduler.schedule(this, scheduleOptions);
            log.info("{}: scheduler {} is added", TAG, schedulerName);
        } else {
            log.info("{}: scheduler {} is disabled", TAG, schedulerName);
            removeScheduler(configuration);
        }
    }

    private void removeScheduler(ProductSwapConfiguration configuration) {
        log.info("{}: removing scheduler {}", TAG, schedulerName);
        scheduler.unschedule(configuration.schedulerName());
    }

    private void saveProduct(ProductDTO productDTO) throws PersistenceException {
        try {
            productService.saveProduct(productDTO);
            log.info("{}: Scheduler successfully saved the product - {}", TAG, productList.get(currentListIndex).getName());
        } catch (PersistenceException e) {
            log.error("{}: Scheduler has found an persistence exception while saving a new product node: ", schedulerName, e.getMessage());
            throw new PersistenceException("Failed to save the product, consider checking if the product list has correct productDTO data that matches productDTO schema", e);
        }
    }

    @Override
    public void run() {
        log.info("{}: Running the scheduled task.", TAG);
        //Resource test = resourceResolver.getResource("com/evandrogoulart/aem/core/schedulers/productswapscheduler/ProductSwitchScheduler.json");
        log.info("{}: Read resource from json...", TAG);
        //ProductDTO productDTO = test.adaptTo(ProductDTO.class);
        log.info("{}: Adapted the resource...", TAG);
        //log.info("{}: Printing the DTO...", TAG, productDTO.toString());*/
        if (productList.isEmpty()) {
            log.error("{}: No productDTOs were found in the list", TAG);
            throw new RuntimeException("No products to swap, consider adding some or deactivating the scheduler.");
        }

        if (currentListIndex < productList.size()) {
            try {
                saveProduct(productList.get(currentListIndex));
            } catch (PersistenceException e) {
                log.error("{}: Encountered an persistence exception while saving a new product node: {}", schedulerName, e.getMessage());
                throw new RuntimeException("Failed to save the product, consider checking if the product list has correct data that matches productDTO ", e);
            }
        } else {
            currentListIndex = 0;
            try {
                saveProduct(productList.get(currentListIndex));
            } catch (PersistenceException e) {
                log.error("{}: Encountered an persistence exception while saving a new product node: {}", schedulerName, e.getMessage());
                throw new RuntimeException("Failed to save the product, consider checking if the product list has correct data that matches productDTO ", e);
            }
        }

        currentListIndex++;
        /*try {
            productService.saveProduct();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }*/
        /*try {
            productService.saveProduct(new ProductDTO(
                    "Intel i9",
                    "Processor",
                    "BRL 1999.99",
                    "/content/dam/jump-shop/asset.jpg"));
        } catch (PersistenceException e) {
            log.error("{}: Encountered an persistence exception while saving a new product node: {}", schedulerName, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }*/
        log.info("{}: Completed the scheduled task successfully.", TAG);

    }

}
