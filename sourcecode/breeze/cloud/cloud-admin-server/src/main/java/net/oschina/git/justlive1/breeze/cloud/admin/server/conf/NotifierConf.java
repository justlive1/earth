package net.oschina.git.justlive1.breeze.cloud.admin.server.conf;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

@Configuration
@ConditionalOnProperty(name = "spring.boot.admin.notify.reminder.enable", havingValue = "true")
@ConditionalOnBean(Notifier.class)
@EnableScheduling
public class NotifierConf {

    @Autowired
    Notifier notifier;

    @Value("${spring.boot.admin.notify.reminder.periodForMinutes:5}")
    Long periodForMinutes;

    @Bean
    public FilteringNotifier filteringNotifier() {
        return new FilteringNotifier(notifier);
    }

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(notifier);
        remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(periodForMinutes));
        return remindingNotifier;
    }

    @Scheduled(fixedRateString = "${spring.boot.admin.notify.reminder.scheduledFixedRate:60000}")
    public void remind() {
        remindingNotifier().sendReminders();
    }
}
