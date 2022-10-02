package de.niko.pcstore.archivator;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@ConditionalOnProperty(value = "enable", prefix = "app.archiver.scheduling", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableScheduling
@ConfigurationProperties("app.archiver.scheduling")
public class Archiver {
    private final EntityManager entityManager;

    public Archiver(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Scheduled(cron = "${app.archiver.scheduling.cron:0 0 * * * *}")
    public void archive() {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM PERSONAL_COMPUTER PC WHERE PC.DATE_OF_DELETION IS NOT NULL");
        List resultList = query.getResultList();

        BigInteger count = (BigInteger) resultList.get(0);

        log.info("Personal computers to archive = " + count);
        log.info("Archiving is coming soon ...");
    }
}
