package uk.gov.hmcts.cp.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.repository.JudgesRepository;

import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class JudgesService {

    private final JudgesRepository judgesRepository;

    private static final Logger LOG = LoggerFactory.getLogger(JudgesService.class);
    private final Boolean stubbedJudges = Boolean.parseBoolean(System.getProperty("STUBBED_JUDGES", "true"));

    public Judges getJudge(final UUID judgeId) {
        if (isNull(judgeId)) {
            LOG.atWarn().log("No  courtId provided");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "courtId is required");
        }

        final Judges judges = judgesRepository.getJudges(judgeId);
        if (nonNull(judges)){
            LOG.atWarn().log("NOTE: System configured to return stubbed judges details. Ignoring provided caseUrn : {}", judgeId);

            LOG.atDebug().log("Court Schedule response: {}", judges);
            return judges;
        } else {
            LOG.error("NO REFERENCE DATA SERVICE CONFIGURED AT THIS TIME");
            throw new NotImplementedException("NO REFERENCE DATA SERVICE CONFIGURED");
        }
    }
}
