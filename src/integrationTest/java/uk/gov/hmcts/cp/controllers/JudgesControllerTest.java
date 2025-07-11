package uk.gov.hmcts.cp.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class JudgesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Should /judges/{judge_id} request with 200 response code")
    @Test
    void shouldCallActuatorAndGet200() throws Exception {
        String judgeId = UUID.randomUUID().toString();
        mockMvc.perform(get("/judges/" + judgeId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
