package justlive.earth.breeze.cloud.admin.server.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListConfig;
import com.hazelcast.config.MapConfig;
import lombok.Data;

@Configuration
@ConditionalOnProperty(name = "spring.boot.admin.hazelcast.enabled", havingValue = "true")
public class HazelcastConf {

  @Data
  @Configuration
  @ConfigurationProperties(prefix = "spring.boot.admin.hazelcast")
  class HazelcastProps {
    String applicationStore;
    Integer applicationBackupCount = 1;
    String applicationEvictionPolicy = "NONE";
    String eventStore;
    Integer eventStoreBackupCount = 1;
    Integer eventStoreMaxSize = 1_000;
  }

  @Bean
  public Config hazelcastConfig(HazelcastProps props) {

    return new Config().setProperty("hazelcast.jmx", "true")
        .addMapConfig(
            new MapConfig(props.applicationStore).setBackupCount(props.applicationBackupCount)
                .setEvictionPolicy(EvictionPolicy.valueOf(props.applicationEvictionPolicy)))
        .addListConfig(new ListConfig(props.eventStore).setBackupCount(props.eventStoreBackupCount)
            .setMaxSize(props.eventStoreMaxSize));
  }
}
