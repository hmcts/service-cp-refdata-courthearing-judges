package uk.gov.hmcts.cp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.services.JudgesService;

@RestController
@RequiredArgsConstructor
public class JudgesController implements JudgesApi {

    private static final Logger log = LoggerFactory.getLogger(JudgesController.class);

    private final JudgesService judgesService;

    @Override
    public ResponseEntity<Judges> getJudgeById(String courtId) {
        log.info("Received request to fetch judge details for courtId: {}", courtId);
        Judges judges = judgesService.getJudge(courtId);
        log.debug("Successfully fetched judge details for courtId: {}", courtId);
        return new ResponseEntity<>(judges, HttpStatus.OK);
    }

}
