package org.qian.business.order.service;

import org.qian.business.order.entity.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface TestService {

    Test get(Long id);

    void save(Test test);

    void update(Test test);

    void delete(Long id);
}
