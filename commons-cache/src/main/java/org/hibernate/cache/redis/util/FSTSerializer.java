package org.hibernate.cache.redis.util;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * FST for Serializer
 */
public class FSTSerializer implements RedisSerializer<Object> {

    private FSTConfiguration fstConfiguration ;

    public FSTSerializer() {
        fstConfiguration = FSTConfiguration.getDefaultConfiguration();
        fstConfiguration.setClassLoader(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return fstConfiguration.asByteArray(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length == 0){
            return null;
        }
        return fstConfiguration.asObject(bytes);
    }
}
