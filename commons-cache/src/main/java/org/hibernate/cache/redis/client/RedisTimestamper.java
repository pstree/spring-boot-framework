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

import java.util.Collections;
import java.util.List;

import org.hibernate.cache.redis.util.CacheTimestamper;

/**
 * @author Johno Crawford (johno@sulake.com)
 */
public class RedisTimestamper implements CacheTimestamper {

  private final RedisClient redisClient;
  private final List<String> timestampCacheKey;

  public RedisTimestamper(RedisClient redisClient, String cacheKey) {
    this.redisClient = redisClient;
    this.timestampCacheKey = Collections.singletonList(cacheKey + "timestamp");
  }

  @Override
  public long next() {
    return redisClient.nextTimestamp(timestampCacheKey);
  }
}
