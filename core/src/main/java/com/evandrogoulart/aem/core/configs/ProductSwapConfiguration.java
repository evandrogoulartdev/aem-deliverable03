package com.evandrogoulart.aem.core.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import static com.evandrogoulart.aem.core.configs.ProductSwapConfiguration.CONFIGURATION_DESCRIPTION;
import static com.evandrogoulart.aem.core.configs.ProductSwapConfiguration.CONFIGURATION_NAME;

@ObjectClassDefinition(
            name = CONFIGURATION_NAME,
            description = CONFIGURATION_DESCRIPTION
)
public @interface ProductSwapConfiguration {

        String CONFIGURATION_NAME = "Product Swap Configuration";
        String CONFIGURATION_DESCRIPTION = "This configuration controls the scheduler behavior for the product swap function used on our pages.";

        @AttributeDefinition(
                name = "Scheduler Name",
                description = "Enter a unique identifier that represents name of the scheduler",
                type = AttributeType.STRING
        )
        String schedulerName() default CONFIGURATION_NAME;

        @AttributeDefinition(
                name = "Enabled",
                description = "Check the box to enable the scheduler",
                type = AttributeType.BOOLEAN
        )
        boolean enabled() default false;

        @AttributeDefinition(
                name = "Cron Expression",
                description = "Cron expression which will decide how the scheduler will run",
                type = AttributeType.STRING
        )
        String cronExpression() default "0/30 * * ? * * *";

}

