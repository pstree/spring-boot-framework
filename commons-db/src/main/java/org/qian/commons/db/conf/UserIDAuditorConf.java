package org.qian.commons.db.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
public class UserIDAuditorConf implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
