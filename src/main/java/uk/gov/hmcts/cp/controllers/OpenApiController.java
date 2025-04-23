package uk.gov.hmcts.cp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judgesschema;
import uk.gov.hmcts.cp.services.OpenApiService;

@RestController
@RequiredArgsConstructor
public class OpenApiController implements JudgesApi {

    private final OpenApiService openApiService;

    @Override
    public ResponseEntity<Judgesschema> getJudgeById(String courtId) {
        Judgesschema judgesschema = openApiService.getJudge(courtId);
        return new ResponseEntity<>(judgesschema, HttpStatus.OK);
    }

}
