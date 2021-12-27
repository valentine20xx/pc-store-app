package de.niko.pcstore.configuration;

import de.niko.pcstore.archivator.Archiver;
import java.util.concurrent.Executors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Slf4j
@ConditionalOnProperty(value = "enable", prefix = "app.archiver.scheduling", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableScheduling
@ConfigurationProperties("app.archiver.scheduling")
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    @Value("${app.archiver.scheduling.cron:0 0 * * * *}")
    @Setter
    private String cron;

    private final Archiver archiver;

    public DynamicSchedulingConfig(Archiver archiver) {
        this.archiver = archiver;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("DynamicSchedulingConfig initialized with cron=" + this.cron);

        scheduledTaskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());
        scheduledTaskRegistrar.addCronTask(archiver::archive, this.cron);
    }
}