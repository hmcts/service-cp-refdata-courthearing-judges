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
import uk.gov.hmcts.cp.services.JudgesService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JudgesControllerTest {

    private static final Logger log = LoggerFactory.getLogger(JudgesControllerTest.class);

    @Mock
    private JudgesService judgesService;

    @InjectMocks
    private JudgesController judgesController;

    private String courtId;

    @BeforeEach
    void setUp() {
        courtId = "test-court-id";
        log.info("Setting up test with courtId: {}", courtId);
    }

    @Test
    void getJudgeById_ShouldReturnJudgesWithOkStatus() {
        // Arrange
        log.info("Starting test: getJudgeById_ShouldReturnJudgesWithOkStatus");
        // Create a mock response that matches the structure from the service
        Judges mockJudges = new Judges();
        when(judgesService.getJudge(courtId)).thenReturn(mockJudges);
        log.debug("Mocked judgesService.getJudge to return: {}", mockJudges);

        // Act
        log.info("Calling judgesController.getJudgeById with courtId: {}", courtId);
        ResponseEntity<?> response = judgesController.getJudgeById(courtId);
        log.debug("Received response: {}", response);

        // Assert
        log.info("Asserting response properties");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockJudges, response.getBody());
        log.info("Test completed successfully");
    }
} 