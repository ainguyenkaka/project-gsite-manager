package com.gsite.app.config.audit;

import com.gsite.app.domain.PersistentAuditEvent;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Component
public class AuditEventConverter {


    public List<AuditEvent> convertToAuditEvent(Iterable<PersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<AuditEvent> auditEvents = new ArrayList<>();
        for (PersistentAuditEvent persistentAuditEvent : persistentAuditEvents) {
            auditEvents.add(convertToAuditEvent(persistentAuditEvent));
        }
        return auditEvents;
    }


    public AuditEvent convertToAuditEvent(PersistentAuditEvent persistentAuditEvent) {
        Instant instant = persistentAuditEvent.getAuditEventDate().atZone(ZoneId.systemDefault()).toInstant();
        return new AuditEvent(Date.from(instant), persistentAuditEvent.getPrincipal(),
            persistentAuditEvent.getAuditEventType(), convertDataToObjects(persistentAuditEvent.getData()));
    }


    public Map<String, Object> convertDataToObjects(Map<String, String> data) {
        Map<String, Object> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                results.put(entry.getKey(), entry.getValue());
            }
        }
        return results;
    }

    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                Object object = entry.getValue();

                // Extract the data that will be saved.
                if (object instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) object;
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else if (object != null) {
                    results.put(entry.getKey(), object.toString());
                } else {
                    results.put(entry.getKey(), "null");
                }
            }
        }

        return results;
    }
}
