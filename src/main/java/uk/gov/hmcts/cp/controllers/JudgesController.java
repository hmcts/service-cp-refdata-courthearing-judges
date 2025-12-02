package uk.gov.hmcts.cp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.services.JudgesService;

import java.util.UUID;

import static java.util.UUID.fromString;

@Slf4j
@RestController
public class JudgesController implements JudgesApi {

    private final JudgesService judgesService;

    public JudgesController(final JudgesService judgesService) {
        this.judgesService = judgesService;
    }

    @Override
    public ResponseEntity<Judges> getJudgeById(final String judgeId) {
        final String sanitizedJudgeId;
        final Judges judges;

        try {
            sanitizedJudgeId = sanitizeJudgeId(judgeId);
            judges = judgesService.getJudge(toUuid(sanitizedJudgeId));
        } catch (ResponseStatusException e) {
            log.error(e.getMessage());
            throw e;
        }

        log.debug("Found Judge response for judgeId: {}", sanitizedJudgeId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(judges);
    }

    private String sanitizeJudgeId(final String judgeId) {
        if (judgeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "judgeId is required");
        }
        return StringEscapeUtils.escapeHtml4(judgeId);
    }

    private UUID toUuid(final String uuid) {
        try {
            return fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UUID format: " + uuid);
        }
    }
}
