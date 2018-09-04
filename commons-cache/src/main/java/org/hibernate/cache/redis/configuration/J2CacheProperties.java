package org.hibernate.cache.redis.configuration;

import java.io.Serializable;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 相关的配置信息
 */

@Getter
@Setter
public class J2CacheProperties implements Serializable {

    private Properties cacheProperties = new Properties();

}
