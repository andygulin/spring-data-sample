package spring.data.sample.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = {"spring.data.sample.repository"}, multicoreSupport = true)
@PropertySource("classpath:solr.properties")
public class SolrConfig {

    @Autowired
    private Environment env;

    @Bean
    public HttpSolrClient httpSolrClient() {
        return new HttpSolrClient(env.getProperty("solr.url"));
    }

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(httpSolrClient());
    }
}