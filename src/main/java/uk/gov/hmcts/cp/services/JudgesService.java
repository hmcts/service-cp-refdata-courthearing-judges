package uk.gov.hmcts.cp.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.Judgesschema;
import uk.gov.hmcts.cp.openapi.model.Judiciary;

@Service
public class JudgesService {

    public Judgesschema getJudge(String courtId) {
        Judiciary judiciary = new Judiciary();
        judiciary.setJohKnownAs("JohKnownAs");
        judiciary.setRole(Judiciary.RoleEnum.JUDGE);
        judiciary.setJohNameSurname("Surname");
        judiciary.setJohTitle("Judge");
        Judgesschema judgesschema = new Judgesschema();
        judgesschema.setJudiciary(judiciary);

        return judgesschema;
    }
}
