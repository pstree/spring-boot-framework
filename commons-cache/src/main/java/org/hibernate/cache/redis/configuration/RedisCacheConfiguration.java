package org.hibernate.cache.redis.configuration;

import java.util.List;
import javax.annotation.PostConstruct;

import org.hibernate.cache.redis.client.RedisClientFactory;
import org.hibernate.cache.redis.util.FSTSerializer;
import org.hibernate.cache.redis.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties({ CacheProperties.class })
@ComponentScan(basePackages = {
    "org.hibernate.cache.redis"
})
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private CacheProperties cacheProperties;

    @Bean("j2CacheRedisTemplate")
    public RedisTemplate<String, Object> j2CacheRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new FSTSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConfigurationProperties(prefix = "cache")
    public J2CacheProperties j2CacheConfig(){
        J2CacheProperties j2CacheProperties = new J2CacheProperties();
        return j2CacheProperties;
    }

    @PostConstruct
    private void init() {
        RedisClientFactory.setRedisTemplate(j2CacheRedisTemplate());
        RedisCacheUtil.loadCacheProperties(j2CacheConfig().getCacheProperties());
    }

    @Bean
    public CacheManager redisCacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(j2CacheRedisTemplate());
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(RedisCacheUtil.getDefaultExpiryInSeconds());
        cacheManager.setExpires(RedisCacheUtil.getExpiresMap());
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }
        return cacheManager;
    }
}