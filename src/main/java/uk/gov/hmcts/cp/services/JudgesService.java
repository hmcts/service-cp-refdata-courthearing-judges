package uk.gov.hmcts.cp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.repository.JudgesRepository;

import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class JudgesService {

    private final JudgesRepository judgesRepository;
    private final Boolean stubbedJudges = Boolean.parseBoolean(System.getProperty("STUBBED_JUDGES", "true"));

    public Judges getJudge(final UUID judgeId) {
        if (isNull(judgeId)) {
            log.warn("No courtId provided");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "courtId is required");
        }

        final Judges judges = judgesRepository.getJudges(judgeId);

        if (nonNull(judges)) {
            log.warn("NOTE: System configured to return stubbed judges details. Ignoring provided caseUrn: {}", judgeId);
            log.debug("Court Schedule response: {}", judges);
            return judges;
        } else {
            log.error("NO REFERENCE DATA SERVICE CONFIGURED AT THIS TIME");
            throw new NotImplementedException("NO REFERENCE DATA SERVICE CONFIGURED");
        }
    }
}
