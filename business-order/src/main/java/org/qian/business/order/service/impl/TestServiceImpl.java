package org.qian.business.order.service.impl;

import org.qian.business.order.dao.TestDao;
import org.qian.business.order.entity.Test;
import org.qian.business.order.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public Test get(Long id) {
        return testDao.findOne(id);
    }

    @Override
    public void save(Test test) {
        testDao.save(test);
    }

    @Override
    public void update(Test test) {
        testDao.save(test);
    }

    @Override
    public void delete(Long id) {
        testDao.delete(id);
    }
}
