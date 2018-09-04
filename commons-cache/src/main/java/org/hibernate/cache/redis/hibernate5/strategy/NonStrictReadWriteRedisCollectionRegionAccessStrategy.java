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

package org.hibernate.cache.redis.hibernate5.strategy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.internal.DefaultCacheKeysFactory;
import org.hibernate.cache.redis.hibernate5.regions.RedisCollectionRegion;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;

/**
 * Redis specific non-strict read/write collection region access strategy
 *
 * @author sunghyouk.bae@gmail.com
 * @since 13. 4. 5. 오후 10:42
 */
@Slf4j
public class NonStrictReadWriteRedisCollectionRegionAccessStrategy
    extends AbstractRedisAccessStrategy<RedisCollectionRegion>
    implements CollectionRegionAccessStrategy {


  public NonStrictReadWriteRedisCollectionRegionAccessStrategy(RedisCollectionRegion region,
                                                               SessionFactoryOptions options) {
    super(region, options);
  }

  @Override
  public Object generateCacheKey(Object id,
                                 CollectionPersister persister,
                                 SessionFactoryImplementor factory,
                                 String tenantIdentifier) {
    return DefaultCacheKeysFactory.staticCreateCollectionKey(id, persister, factory, tenantIdentifier);
  }

  @Override
  public Object getCacheKeyId(Object cacheKey) {
    return DefaultCacheKeysFactory.staticGetCollectionId(cacheKey);
  }

  @Override
  public CollectionRegion getRegion() {
    return region;
  }

  @Override
  public boolean putFromLoad(SessionImplementor session,
                             Object key,
                             Object value,
                             long txTimestamp,
                             Object version,
                             boolean minimalPutOverride) {
    if (minimalPutOverride && region.contains(key)) {
      return false;
    }
    region.put(key, value);
    return true;
  }

  @Override
  public Object get(SessionImplementor session, Object key, long txTimestamp) throws CacheException {
    return region.get(key);
  }


}
