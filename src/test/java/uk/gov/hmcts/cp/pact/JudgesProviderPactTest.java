package uk.gov.hmcts.cp.pact;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary;
import uk.gov.hmcts.cp.repository.JudgesRepository;

import java.util.UUID;
import static java.util.UUID.fromString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, PactVerificationInvocationContextProvider.class})
@Provider("VPJudgesProvider")
@PactBroker(
        scheme = "https",
        host = "${pact.broker.host}",
        authentication = @PactBrokerAuth(token = "${pact.broker.token}")
)
@Tag("pact")
public class JudgesProviderPactTest {

    @Autowired
    private JudgesRepository judgesRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupTarget(PactVerificationContext context) {
        System.out.println("Running test on port: " + port);
        context.setTarget(new HttpTestTarget("localhost", port));
        System.out.println("pact.verifier.publishResults: " + System.getProperty("pact.verifier.publishResults"));
    }

    @State("judge exists")
    public void setUpJudges() {
        judgesRepository.clearAll();
        final UUID judgeId = fromString("a228cbdb-e1d0-4d29-bb44-06b7669b66a3");
        final JudgesJudiciary judiciary = JudgesJudiciary.builder()
                .johTitle("His Honour")
                .johNameSurname("John Smith")
                .role(JudgesJudiciary.RoleEnum.fromValue("judge"))
                .johKnownAs("His Honour Judge Smith")
                .build();
        final Judges judges = new Judges();
        judges.setJudiciary(judiciary);
        judgesRepository.saveJudges(judgeId, judges);
    }

    @TestTemplate
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
