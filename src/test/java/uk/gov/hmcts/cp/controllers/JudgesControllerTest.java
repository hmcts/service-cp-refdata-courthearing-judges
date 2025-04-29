package uk.gov.hmcts.cp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.Judiciary;
import uk.gov.hmcts.cp.services.JudgesService;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JudgesControllerTest {

    private static final Logger log = LoggerFactory.getLogger(JudgesControllerTest.class);
    
    @BeforeEach
    void setUp() {
    }

    @Test
    void getJudgeById_ShouldReturnJudgesWithOkStatus() {
        JudgesController judgesController = new JudgesController(new JudgesService());
        UUID courtId = UUID.randomUUID();
        log.info("Calling judgesController.getJudgeById with courtId: {}", courtId);
        ResponseEntity<?> response = judgesController.getJudgeById(courtId.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Judiciary judiciary = new Judiciary();
        judiciary.setJohKnownAs("JohKnownAs");
        judiciary.setRole(Judiciary.RoleEnum.JUDGE);
        judiciary.setJohNameSurname("Surname");
        judiciary.setJohTitle("Judge");
        Judges stubbedJudges = new Judges();
        stubbedJudges.setJudiciary(judiciary);

        assertEquals(stubbedJudges, response.getBody());
    }

    @Test
    void getJudgeById_ShouldReturnBadRequestStatus() {
        JudgesController judgesController = new JudgesController(new JudgesService());

        log.info("Calling judgesController.getJudgeById with null courtId");
        ResponseEntity<?> response = judgesController.getJudgeById(null);
        log.debug("Received response: {}", response);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
} 