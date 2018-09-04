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

package org.hibernate.cache.redis.client;

import java.util.*;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.redis.util.RedisCacheUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * RedisClient implemented using Redisson library
 * <p>
 * see https://github.com/mrniko/redisson
 *
 * WARNING: no default constructor to avoid automatically creating Redis client.
 *
 * @author debop sunghyouk.bae@gmail.com
 */
@Slf4j
public class RedisClient {

  private static final String REDIS_KEY_ALL = "*";
  private static final String REDIS_KEY_SPACER = ":";

  @Getter
  private static final RedisTemplate<String, Object> redisson = RedisClientFactory.getRedisTemplate();

  @SneakyThrows
  public RedisClient() {
    log.trace("RedisClient created.");
  }

  public long nextTimestamp(final List<String> keys) {
    DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<Integer>();
    redisScript.setScriptText("redis.call('setnx', KEYS[1], ARGV[1]); " +  "return redis.call('incr', KEYS[1]);");
    redisScript.setResultType(Integer.class);
    return redisson.execute(redisScript,keys,System.currentTimeMillis());
  }

  public long dbSize() {
    return redisson.keys(REDIS_KEY_ALL).size();
  }

  public boolean exists(final String region, final Object key) {
    return redisson.hasKey(region + REDIS_KEY_SPACER + key);
  }

  public <T> T get(final String region, final Object key) {
    T cacheItem = (T) redisson.opsForValue().get(region + REDIS_KEY_SPACER + key);
    log.trace("retrieve cache item. region={}, key={}, value={}", region, key, cacheItem);
    return cacheItem;
  }

  public boolean isExpired(final String region, final Object key) {
    return exists(region, key);
  }

  public Set<String> keysInRegion(final String region) {
    return redisson.keys(region + REDIS_KEY_SPACER + REDIS_KEY_ALL);
  }

  public long keySizeInRegion(final String region) {
    return redisson.keys(region + REDIS_KEY_SPACER + REDIS_KEY_ALL).size();
  }

  public Map<String, Object> getAll(final String region) {
    Set<String> set = redisson.keys(region + REDIS_KEY_SPACER + REDIS_KEY_ALL);
    Map<String,Object> map = new HashMap<String, Object>(set.size());
    for (String key: set) {
      map.put(key , redisson.opsForValue().get(key));
    }
    return map;
  }

  public void set(final String region, final Object key, Object value) {
    set(region, key, value, RedisCacheUtil.getExpiryInSeconds(region));
  }

  public void set(final String region, final Object key, Object value, final long timeoutInSeconds) {
    set(region, key, value, timeoutInSeconds, TimeUnit.SECONDS);
  }

  public void set(final String region, final Object key, Object value, final long timeout, final TimeUnit unit) {
    log.trace("set cache item. region={}, key={}, timeout={}, unit={}", region, key, timeout, unit);
    if (timeout > 0L) {
      redisson.opsForValue().set(region + REDIS_KEY_SPACER + key, value, timeout, unit);
    } else {
      redisson.opsForValue().set(region + REDIS_KEY_SPACER + key, value);
    }
  }

  public void del(final String region, final Object key) {
    redisson.delete(region + REDIS_KEY_SPACER + key);
  }

  public void deleteRegion(final String region) {
    redisson.delete(region + REDIS_KEY_SPACER + REDIS_KEY_ALL);
  }

  public void shutdown(){

  }
}
