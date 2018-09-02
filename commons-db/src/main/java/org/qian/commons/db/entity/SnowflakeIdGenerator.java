package org.qian.commons.db.entity;

import org.qian.commons.sequence.Sequence;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private static final Sequence sequence = new Sequence(Long.parseLong(new Random().nextInt(32)+""),Long.parseLong(new Random().nextInt(32)+""));

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        return sequence.nextId().toString();
    }
}
