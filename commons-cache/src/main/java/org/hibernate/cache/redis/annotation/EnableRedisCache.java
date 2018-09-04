package org.hibernate.cache.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.cache.redis.configuration.RedisCacheConfiguration;
import org.springframework.context.annotation.Import;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import(RedisCacheConfiguration.class)
public @interface EnableRedisCache {

}
