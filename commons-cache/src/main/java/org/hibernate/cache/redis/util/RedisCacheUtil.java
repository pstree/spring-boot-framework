/*
 * Copyright (c) 2017. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.hibernate.cache.redis.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * Hibernate Redis Cache Utility Class
 *
 * @author debop sunghyouk.bae@gmail.com
 */
@Slf4j
public final class RedisCacheUtil {


  public final static String PREFIX_REGION = "region.";
  public final static String DEFAULT_REGION = "region.default";

  public final static String DEFAULT_SPLIT = ",";

  public static final String EXPIRY_PROPERTY_PREFIX = "region";
  public static final String DEFAULT_EXPIRY_IN_SECONDS = "100,2m";

  private static final Properties cacheProperties = new Properties();

  private RedisCacheUtil() { }

  public static Long getDefaultExpiryInSeconds() {
    return getExpiryInSeconds("default");
  }

  /**
   * Load hibernate-redis.properties
   *
   * @param props hibernate configuration
   * @return hibernate-redis configuration
   */
  public static Properties loadCacheProperties(final Properties props) {
    cacheProperties.putAll(props);
    return cacheProperties;
  }

  /**
   * Get Redisson configuration file path (redisson.yaml)
   *
   * @return Redisson configuration yaml file path
   */
  public static String getRedissonConfigPath() {
    return "";
  }


  /**
   * get expiry timeout (seconds) by region name.
   *
   * @param region region name
   * @return expiry (seconds)
   */
  public static Long getExpiryInSeconds(final String region) {
    try {
      String key = EXPIRY_PROPERTY_PREFIX + "." + region;
      String value = getProperty(key, String.valueOf(DEFAULT_EXPIRY_IN_SECONDS));

      log.trace("load expiry property. region={}, expiryInSeconds={}", key, value);

      return getExpire(value);
    } catch (Exception e) {
      log.warn("Fail to get expiryInSeconds in region={}", region, e);
      return getExpire(DEFAULT_EXPIRY_IN_SECONDS);
    }
  }

  /**
   * get property value
   *
   * @param key          property key
   * @param defaultValue default value for not found key
   * @return property value
   */
  public static String getProperty(final String key, String defaultValue) {
    if (cacheProperties == null || cacheProperties.isEmpty()) {
      return defaultValue;
    }
    try {
      String value = cacheProperties.getProperty(key, defaultValue);
      log.trace("get property. key={}, value={}, defaultValue={}", key, value, defaultValue);
      return value;
    } catch (Exception ignored) {
      log.warn("error occurred in reading properties. key={}", key, ignored);
      return defaultValue;
    }
  }

  /**
   *
   * @return
   */
  public static Map<String,Long> getExpiresMap() {
    Map<String, Long> map = new HashMap<String, Long>();
    for (String region : cacheProperties.stringPropertyNames()) {
      if (!region.startsWith(RedisCacheUtil.DEFAULT_REGION)) {
        String expires = cacheProperties.getProperty(region).trim();
        region = region.substring(RedisCacheUtil.PREFIX_REGION.length());
        map.put(region, RedisCacheUtil.getExpire(expires));
      }
    }
    return map;
  }

  /**
   *
   * @param value
   * @return
   */
  public static Long getExpire(String value){
    String[] cfgs = value.split(DEFAULT_SPLIT);
    String sExpire = cfgs[1].trim();
    char unit = Character.toLowerCase(sExpire.charAt(sExpire.length()-1));
    Long expire = Long.parseLong(sExpire.substring(0, sExpire.length() - 1));
    switch(unit){
      case 's':
        break;
      case 'm':
        expire *= 60;
        break;
      case 'h':
        expire *= 3600;
        break;
      case 'd':
        expire *= 86400;
        break;
      default:
        throw new IllegalArgumentException("Unknown expire unit:" + unit);
    }
    return expire;
  }

}