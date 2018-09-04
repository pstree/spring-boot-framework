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

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Factory class for RedisClient.
 *
 * @author debop sunghyouk.bae@gmail.com
 */
@Slf4j
public final class RedisClientFactory {

  private static final RedisClientFactory REDIS_CLIENT_FACTORY = new RedisClientFactory();

  private RedisTemplate<String,Object> redisTemplate;

  public static RedisTemplate<String, Object> getRedisTemplate() {
    return REDIS_CLIENT_FACTORY.redisTemplate;
  }

  public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
    REDIS_CLIENT_FACTORY.redisTemplate = redisTemplate;
  }

  public static RedisClient createRedisClient(String path){
    return new RedisClient();
  }
}
