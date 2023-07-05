package com.evandrogoulart.aem.core.schedulers;

import com.evandrogoulart.aem.core.configs.ProductSwapConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

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
    Scheduler scheduler;
    private String schedulerName;

    @Activate
    protected void activate(ProductSwapConfiguration configuration) {
        this.schedulerName = configuration.schedulerName();
    }

    @Modified
    protected void modified(ProductSwapConfiguration configuration) {
        removeScheduler(configuration);
        addScheduler(configuration);
    }

    @Deactivate
    protected void deactivate(ProductSwapConfiguration configuration) {
        removeScheduler(configuration);
    }

    private void addScheduler(ProductSwapConfiguration configuration) {
        if (configuration.enabled()) {
            ScheduleOptions scheduleOptions = scheduler.EXPR(configuration.cronExpression());
            scheduleOptions.name(schedulerName);
            scheduleOptions.canRunConcurrently(false);
            scheduler.schedule(this, scheduleOptions);
        } else {
            removeScheduler(configuration);
        }
    }

    private void removeScheduler(ProductSwapConfiguration configuration) {
        scheduler.unschedule(configuration.schedulerName());
    }

    @Override
    public void run() {
        log.info("{}: Running the scheduled task successfully", TAG);
    }

}
