package uk.gov.hmcts.cp.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.cp.controllers.JudgesController;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.repository.JudgesRepository;
import uk.gov.hmcts.cp.pact.helper.JsonFileToObject;

import java.util.UUID;
import static java.util.UUID.fromString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, PactVerificationInvocationContextProvider.class})
@Provider("CPRefDataJudgesProvider")
@PactBroker(
        url = "${pact.broker.url}",
        authentication = @PactBrokerAuth(token = "${pact.broker.token}")
)
@Tag("pact")
public class JudgesProviderPactTest {

    private static final Logger LOG = LoggerFactory.getLogger(JudgesProviderPactTest.class);

    private static final UUID STUBBED_JUDGE_ID = fromString("a228cbdb-e1d0-4d29-bb44-06b7669b66a3");

    @Autowired
    private JudgesRepository judgesRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupTarget(PactVerificationContext context) {
        LOG.atDebug().log("Running test on port: " + port);
        context.setTarget(new HttpTestTarget("localhost", port));
        LOG.atDebug().log("pact.verifier.publishResults: " + System.getProperty("pact.verifier.publishResults"));
    }

    @State("judge exists")
    public void setUpJudges() throws Exception {
        judgesRepository.clearAll();
        LOG.atDebug().log("Setting up judges for judgeId: {}", STUBBED_JUDGE_ID);
        Judges judges = JsonFileToObject.readJsonFromResources("judges.json", Judges.class);
        judgesRepository.saveJudges(STUBBED_JUDGE_ID, judges);
    }

    @TestTemplate
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
