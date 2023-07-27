package com.evandrogoulart.aem.core.services.impl;

import com.evandrogoulart.aem.core.services.ResourceResolverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component(service = ResourceResolverService.class)
public class ResourceResolverServiceImpl implements ResourceResolverService {
    @Reference
    private ResourceResolverFactory resolverFactory;
    @Override
    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "ResourceResolverService");
        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
            log.info("Logged in to the resource resolver successfully with the following user ID: {}", resolver.getUserID());
        } catch (LoginException e) {
            log.error("Error logging in resolverFactory and retrieving a service resource resolver: ", e.getMessage());
            throw new RuntimeException("Failed to login in the resolverFactory, make sure the resolver service is mapped to a user with the correct permissions.", e);
        }
        return resolver;
    }
}