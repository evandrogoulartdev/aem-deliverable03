# AEM Track - Backend - Webjump Jump Shop Project

This project is a deliverable that aims to implement a digital store for tech components, delivering a couple features required by the AEM Backend Track.
* One of those features, the Scheduler, is an OSGi component that can be activated to change the Product Model displayed on the page, by saving new product nodes, every 30 seconds. The Scheduler also logs every single iteration.
* Another feature is a Servlet that processes POST requests, changing the Product Model node on demand. It's also mapped to the endpoint: /bin/products/secrets/save. Also, the payload of requests should be in JSON format and follow the Product Model properties.   

## Required setup 

- A system user with Modify/Create/Read ACL permissions must be mapped to the project's ResourceResolverService. In your AEM Author instance, simply go to the Apache Sling Service User Mapper Service in the OSGi Console, set up "jump-shop.core:ResourceResolverService=systemuser" as Service Mappings and you're good to go. Keep in mind that "systemuser" must be created and have the proper ACL permissions.

## Model properties 

The properties of the Product Model, which should match the JSON payload of requests, are as follows:

* String name: Name of the product. (TODO: Implement constraints and validators)
* String category: Category which the product belongs to. (TODO: Implement constraints and validators)
* String price: Price of the product. (TODO: Implement constraints and validators)
* String fileReference: Path of the product image stored in the DAM repository. (TODO: Implement constraints and validators)

## How to build and deploy the project

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage
