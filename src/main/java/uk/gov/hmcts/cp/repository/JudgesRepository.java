package uk.gov.hmcts.cp.repository;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.cp.openapi.model.Judges;

import java.util.UUID;

@Repository
public interface JudgesRepository {

    Judges getJudges(UUID judgeId);
    void saveJudges(UUID judgeId, Judges judges);
    void clearAll();

}
