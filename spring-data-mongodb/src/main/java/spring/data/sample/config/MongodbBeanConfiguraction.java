package spring.data.sample.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = "spring.data.sample.repository")
public class MongodbBeanConfiguraction {

    private
    @Value("${mongodb.host}")
    String host;
    private
    @Value("${mongodb.port}")
    int port;
    private
    @Value("${mongodb.dbName}")
    String dbName;
    private
    @Value("${mongodb.username}")
    String username;
    private
    @Value("${mongodb.password}")
    String password;
    private
    @Value("${mongodb.connectionsPerHost}")
    int connectionsPerHost;
    private
    @Value("${mongodb.threadsAllowedToBlockForConnectionMultiplier}")
    int threadsAllowedToBlockForConnectionMultiplier;
    private
    @Value("${mongodb.connectTimeout}")
    int connectTimeout;
    private
    @Value("${mongodb.maxWaitTime}")
    int maxWaitTime;
    private
    @Value("${mongodb.socketKeepAlive}")
    boolean socketKeepAlive;
    private
    @Value("${mongodb.socketTimeout}")
    int socketTimeout;

    @Bean
    public MongoClient mongo() throws UnknownHostException {
        Builder builder = MongoClientOptions.builder();
        builder.connectionsPerHost(connectionsPerHost).connectTimeout(connectTimeout).maxWaitTime(maxWaitTime)
                .socketKeepAlive(socketKeepAlive).connectTimeout(connectTimeout)
                .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        MongoClientOptions mongoClientOptions = builder.build();
        return new MongoClient(new ServerAddress(host, port), mongoClientOptions);
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(mongo(), dbName);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws UnknownHostException {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    public DbRefResolver dbRefResolver() throws UnknownHostException {
        return new DefaultDbRefResolver(mongoDbFactory());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() throws UnknownHostException {
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver(), mappingContext());
        converter.setTypeMapper(defaultMongoTypeMapper());
        return converter;
    }

    @Bean
    public MongoMappingContext mappingContext() {
        return new MongoMappingContext();
    }

    @Bean
    public DefaultMongoTypeMapper defaultMongoTypeMapper() {
        return new DefaultMongoTypeMapper(null);
    }
}
