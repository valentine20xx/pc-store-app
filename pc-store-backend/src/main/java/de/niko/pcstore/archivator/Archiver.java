package de.niko.pcstore.archivator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@ConditionalOnProperty(value = "enable", prefix = "app.archiver.scheduling", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableScheduling
@ConfigurationProperties("app.archiver.scheduling")
public class Archiver {
    @Scheduled(cron = "${app.archiver.scheduling.cron:0 0 * * * *}")
    public void archive() {
        log.info("Archiving is coming soon ...");
    }
}
