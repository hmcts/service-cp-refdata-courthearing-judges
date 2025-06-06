package uk.gov.hmcts.cp.services;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary.RoleEnum;

import java.util.UUID;

@Service
public class JudgesService implements StubbedJudges {

    private static final Logger LOG = LoggerFactory.getLogger(JudgesService.class);
    private final Boolean stubbedJudges = Boolean.parseBoolean(System.getProperty("STUBBED_JUDGES", "true"));

    public Judges getJudge(final UUID courtId) {
        if (stubbedJudges) {
            LOG.atInfo().log("System configured to return stubbed Judge details. Ignoring provided courtId : {}", courtId.toString());
            return getStubbedJudge();
        }else {
            LOG.error("NO REFERENCE DATA SERVICE CONFIGURED AT THIS TIME");
            throw new NotImplementedException("NO REFERENCE DATA SERVICE CONFIGURED");
        }
    }

    public Judges getStubbedJudge() {
        final JudgesJudiciary judiciary = JudgesJudiciary.builder()
                .johTitle("His Honour")
                .johNameSurname("John Smith")
                .role(RoleEnum.fromValue("judge"))
                .johKnownAs("His Honour Judge Smith")
                .build();
        final Judges judges = new Judges();
        judges.setJudiciary(judiciary);

        return judges;
    }
}
