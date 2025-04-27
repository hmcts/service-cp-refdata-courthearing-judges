package uk.gov.hmcts.cp.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.Judiciary;

@Service
public class JudgesService {

    public Judges getJudge(String courtId) {
        Judiciary judiciary = new Judiciary();
        judiciary.setJohKnownAs("JohKnownAs");
        judiciary.setRole(Judiciary.RoleEnum.JUDGE);
        judiciary.setJohNameSurname("Surname");
        judiciary.setJohTitle("Judge");
        Judges judges = new Judges();
        judges.setJudiciary(judiciary);

        return judges;
    }
}
