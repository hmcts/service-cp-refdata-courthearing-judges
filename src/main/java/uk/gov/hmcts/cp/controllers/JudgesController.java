package uk.gov.hmcts.cp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.services.JudgesService;

import java.util.UUID;

@RestController
public class JudgesController implements JudgesApi {

    private static final Logger LOG = LoggerFactory.getLogger(JudgesController.class);
    private final JudgesService judgesService;

    public JudgesController(final JudgesService judgesService) {
        this.judgesService = judgesService;
    }

    @Override
    public ResponseEntity<Judges> getJudgeById(final String courtId) {
        final ResponseEntity<Judges> response;
        if (courtId == null) {
            LOG.atWarn().log("No court identifier provided: {}", HttpStatus.BAD_REQUEST);
            response = ResponseEntity.badRequest().build();
        } else {

            final UUID cId = UUID.fromString(courtId);
            LOG.atDebug().log("Received request to fetch judge details for courtId: {}", cId.toString());
            final Judges judges = judgesService.getJudge(cId);
            LOG.atDebug().log("Successfully found judge details : {}", judges);
            response = new ResponseEntity<>(judges, HttpStatus.OK);
        }
        return response;
    }

}
