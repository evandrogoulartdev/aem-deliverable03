# AEM Backend Track - Webjump Jump Shop Project

This project is a deliverable that aims to implement a digital store, delivering a couple features required by the AEM Backend Track.
One of them, the Scheduler, is an OSGi component that can be activated in order to change the Product Model node every 30 seconds.
The Scheduler also logs every single iteration.
Another feature is a Servlet that processes POST requests, changing the Product Model node on demand. It's also mapped to the endpoint: /bin/products/secrets/save. 
Also, the payload of requests should be in JSON format and follow the Product Model properties.   

## Required setup process

- A system user with Modify/Create/Read ACL permissions must be mapped to the project's ResourceResolverService. Simply go to the Apache Sling Service User Mapper Service, set up "jump-shop.core:ResourceResolverService=(systemuser)" and this requirement should be completed.

## Model properties and how to use them

The properties of the Product Model, which should also be the same for the JSON payload of requests, are as follows:

* String name: Name of the product, no restrictions/validations. (TODO: Implement constraints e validators)
* String category: Category which the product belongs to, no restrictions/validations. (TODO: Implement constraints e validators)
* String price: Price of the product, no restrictions/validations. (TODO: Implement constraints e validators)
* String fileReference: Path of the image stored in the DAM repository to be rendered as part of the Product Model, no restrictions/validations. (TODO: Implement constraints e validators)

## How to build and deploy the project

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage
