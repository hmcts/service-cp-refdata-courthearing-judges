package uk.gov.hmcts.cp.repository;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.cp.openapi.model.Judges;

import java.util.UUID;

//@FunctionalInterface
@Repository
public interface JudgesRepository {

    Judges getJudges(UUID judgeId);
    void saveJudges(final UUID judgeId, final Judges judges);
    void clearAll();

}
