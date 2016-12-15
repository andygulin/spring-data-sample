package spring.data.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SolrConfig.class)
public class AppConfig {

}
