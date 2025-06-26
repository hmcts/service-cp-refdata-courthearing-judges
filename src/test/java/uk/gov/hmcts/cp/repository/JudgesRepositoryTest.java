package uk.gov.hmcts.cp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JudgesRepositoryTest {

    private JudgesRepository judgesRepository;

    @BeforeEach
    void setUp() {
        judgesRepository = new InMemoryJudgesRepositoryImpl();
    }

    @Test
    void getCourtScheduleByCaseUrn_shouldReturnCourtScheduleResponse() {
        UUID judgeId = UUID.randomUUID();

        Judges response = judgesRepository.getJudges(judgeId);
        assertNotNull(response.getJudiciary());

        JudgesJudiciary judgesJudiciary = response.getJudiciary();
        assertEquals("judge", judgesJudiciary.getRole().toString());
    }
}