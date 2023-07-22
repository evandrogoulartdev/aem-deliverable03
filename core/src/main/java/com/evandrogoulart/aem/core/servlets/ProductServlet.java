package com.evandrogoulart.aem.core.servlets;

import com.evandrogoulart.aem.core.dtos.ProductDTO;
import com.evandrogoulart.aem.core.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "jump-shop/components/page",
        methods = HttpConstants.METHOD_POST,
        selectors = "secret",
        extensions = "json")
@SlingServletPaths("/bin/products/secret")
public class ProductServlet extends SlingAllMethodsServlet {
    @Reference
    ProductService productService;
    @Override
    protected void doPost(final @NonNull SlingHttpServletRequest request,
                          final @NonNull SlingHttpServletResponse response) throws ServletException, IOException {

        final ProductDTO productDTO = request.getInputStream() != null ? new ObjectMapper().readValue(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8), ProductDTO.class) : null;

        if (productDTO != null) {
            try {
                productService.saveProduct(productDTO);
            } catch (IOException e) {
                log.error("ProductServlet has encountered an I/O exception while persisting a new productDTO: {}", e.getMessage());
                response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving the request data. Verify if the request payload matches our product schema.");
                throw e;
            }

            response.setStatus(SlingHttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write("Your favorite product has been secretly selected for a special sale on our main page! Enjoy!");
        } else {
            response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving the request data. Verify if the request payload isn't empty.");
        }

    }
}
