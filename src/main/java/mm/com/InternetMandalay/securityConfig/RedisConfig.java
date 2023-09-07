package mm.com.InternetMandalay.securityConfig;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.connection.ConnectionListener;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.net.InetSocketAddress;
import java.time.Duration;

@Configuration
//@EnableRedisRepositories
@EnableCaching
public class RedisConfig {
    @Autowired
    RedisProperties redisProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson(){
        Config config =new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword())
                .setDatabase(1);
        config.setConnectionListener(new ConnectionListener() {
            @Override
            public void onConnect(InetSocketAddress addr) {

            }

            @Override
            public void onDisconnect(InetSocketAddress addr) {

            }
        });
        return Redisson.create(config);
    }

    @Bean
    public RedissonConnectionFactory redisConnectionFactory() {
        return new RedissonConnectionFactory(redisson());
    }


    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
        return (builder) -> builder
                .withCacheConfiguration("Customer", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
    }

}
