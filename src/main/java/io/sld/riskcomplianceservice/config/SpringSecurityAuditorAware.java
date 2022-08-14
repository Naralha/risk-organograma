package io.sld.riskcomplianceservice.config;

import java.util.Optional;

import io.sld.riskcomplianceservice.constants.HttpHeaderConstants;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Component for SpringSecurityAuditorAware custom implementation
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /** {@inheritDoc} */
    @Override
    public Optional<String> getCurrentAuditor() {

        // return Optional.of(Optional.ofNullable(SecurityContextHolder.getContext())
        //                .map(SecurityContext::getAuthentication)
        //                .filter(Authentication::isAuthenticated)
        //                .map(Authentication::getName)
        //                .orElse("undefined"));

        return Optional.of(Optional.ofNullable((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(req -> req.getHeader(HttpHeaderConstants.X_REQ_ORIGIN))
                .orElse("undefined"));
    }

}
