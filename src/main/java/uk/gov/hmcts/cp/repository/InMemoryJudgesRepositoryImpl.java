package uk.gov.hmcts.cp.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class InMemoryJudgesRepositoryImpl implements JudgesRepository {

    private final Map<UUID, Judges> judgesMap = new ConcurrentHashMap<>();

    public void saveJudges(final UUID courtId, final Judges judges) {
        judgesMap.put(courtId, judges);
    }

    public Judges getJudges(final UUID judgeId) {
        if (!judgesMap.containsKey(judgeId)) {
            log.debug("No judges found for judgeId {}", judgeId);
            saveJudges(judgeId, createJudges());
        }
        return judgesMap.get(judgeId);
    }

    public void clearAll() {
        judgesMap.clear();
    }

    private Judges createJudges() {
        final JudgesJudiciary judiciary = JudgesJudiciary.builder()
                .johTitle("His Honour")
                .johNameSurname("John Smith")
                .role(JudgesJudiciary.RoleEnum.fromValue("judge"))
                .johKnownAs("His Honour Judge Smith")
                .build();

        final Judges judges = new Judges();
        judges.setJudiciary(judiciary);

        log.debug("Judges created: {}", judges);
        return judges;
    }
}
