package com.example.hazelcaset.dynamo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class RestrictionServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RestrictionCodeRequest restrictionCodeRequest;
    @InjectMocks
    private RestrictionService restrictionService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(restrictionService, "outboundBaseUrl", "http://YourURL");
        ReflectionTestUtils.setField(restrictionService, "addRestrictionEndpoint", "http://YourURL");
        ReflectionTestUtils.setField(restrictionService, "owner", "12345");
    }

    @Test
    public void doRestrictionActionTest() {
        Case caseObj = new Case();
        RestrictionCodeRequest request = new RestrictionCodeRequest();
        StandardResponse standardResponse = new StandardResponse();
        //TODO add StatusData (with code and Message) and Data
        //standardResponse.setStatusData();
        ResponseEntity<StandardResponse> responseResponseEntity = ResponseEntity.ok(standardResponse);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),
                                            Mockito.any(HttpMethod.class),
                                            Mockito.any(HttpEntity.class),
                                            Mockito.any(StandardResponse.class)))
                .thenReturn(responseResponseEntity);
        restrictionService.doRestrictionAction(request, caseObj);
        Assertions.assertTrue(Objects.isNull(caseObj.getAmlssrestMessage()));
        Assertions.assertTrue(Objects.nonNull(caseObj.getAmlssreststatus()));
    }

    @Test
    public void doRestrictionActionWithExceptionTest() {
        Case caseObj = new Case();
        RestrictionCodeRequest request = new RestrictionCodeRequest();
        StandardResponse standardResponse = new StandardResponse();
        //TODO add StatusData (with code and Message) and Data
        //standardResponse.setStatusData();
        ResponseEntity<StandardResponse> responseResponseEntity = ResponseEntity.ok(standardResponse);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.any(HttpEntity.class),
                        Mockito.any(StandardResponse.class)))
                .thenThrow(new Exception("API call failed"));
        restrictionService.doRestrictionAction(request, caseObj);
        Assertions.assertTrue("EXCEPTION".equals(caseObj.getAmlssreststatus()));
    }
}
