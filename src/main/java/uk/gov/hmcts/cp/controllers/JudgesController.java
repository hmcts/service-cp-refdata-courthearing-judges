package uk.gov.hmcts.cp.controllers;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.services.JudgesService;

import static java.util.UUID.fromString;

@RestController
public class JudgesController implements JudgesApi {

    private static final Logger LOG = LoggerFactory.getLogger(JudgesController.class);
    private final JudgesService judgesService;

    public JudgesController(final JudgesService judgesService) {
        this.judgesService = judgesService;
    }

    @Override
    public ResponseEntity<Judges> getJudgeById(final String judgeId) {
        final String sanitizedJudgeId;
        final Judges judges;

       try{
           sanitizedJudgeId = sanitizeJudgeId(judgeId);
           judges = judgesService.getJudge(fromString(sanitizedJudgeId));
       } catch (ResponseStatusException e) {
           LOG.atError().log(e.getMessage());
           throw e;
       }
        LOG.debug("Found Judge response for judgeId: {}", sanitizedJudgeId);
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

}
