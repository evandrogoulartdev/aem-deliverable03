package com.evandrogoulart.aem.core.services.impl;

import com.evandrogoulart.aem.core.services.getResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component(service = getResolver.class)
public class getResolverImpl implements getResolver {
    @Reference
    ResourceResolverFactory resolverFactory;
    @Override
    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "getResolver");
        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            log.error("Error loging in resolverFactory and retrieving a service resource resolver: ", e.getMessage());
            e.getMessage();
        }
        return resolver;
    }
}