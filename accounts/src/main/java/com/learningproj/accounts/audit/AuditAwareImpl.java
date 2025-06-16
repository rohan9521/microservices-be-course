package com.learningproj.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // hard coding now . will be dynamically passed when spring security is integrated
        //Current auditor
        return Optional.of("ACCOUNTS_MS");
    }
}
