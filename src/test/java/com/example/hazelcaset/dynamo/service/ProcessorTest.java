package com.example.hazelcaset.dynamo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessorTest {
    @Mock
    private Alert cdsRequest;
    @Mock
    private CaseCreationUtils caseCreationUtils;
    @Mock
    private FWCRequestMapper fcwRequestMapper;
    @Mock
    ScreeningMessage<CustomScreeningCase> customScreeningCaseScreeningMessage;
    @Mock
    private CdsService cdsService;
    @InjectMocks
    private Processor processor;

    @Test
    public void process_test() {
        Mockito.when(caseCreationUtils.getCaseId(Mockito.any())).thenReturn("0012A232CSDJ");
        Mockito.when(fcwRequestMapper.convert2FCWRequest(Mockito.any())).thenReturn(cdsRequest);
        Mockito.doNothing().when(cdsService).post(Mockito.any(), Mockito.any());
        Mockito.when(customScreeningCaseScreeningMessage.getInputMessage()).thenReturn("Test Message");

        processor.process("test", customScreeningCaseScreeningMessage);

        Mockito.verify(cdsService, Mockito.times(1)).post(Mockito.any());
    }

    @Test
    public void processWithProcessFailureException_test() {
        Mockito.when(caseCreationUtils.getCaseId(Mockito.any())).thenReturn("0012A232CSDJ");
        Mockito.when(fcwRequestMapper.convert2FCWRequest(Mockito.any())).thenReturn(cdsRequest);
        Mockito.doThrow(FailureProcessException.class).when(cdsService).post(Mockito.any(), Mockito.any());
        Mockito.when(customScreeningCaseScreeningMessage.getInputMessage()).thenReturn("Test Message");

        Assertions.assertThrows(ProcessFailureException.class, () -> processor.process("test", customScreeningCaseScreeningMessage));
    }

    @Test
    public void processWithFCWException_test() {
        Mockito.when(caseCreationUtils.getCaseId(Mockito.any())).thenReturn("0012A232CSDJ");
        Mockito.when(fcwRequestMapper.convert2FCWRequest(Mockito.any())).thenReturn(cdsRequest);
        Mockito.doThrow(FCWException.class).when(cdsService).post(Mockito.any(), Mockito.any());
        Mockito.when(customScreeningCaseScreeningMessage.getInputMessage()).thenReturn("Test Message");

        Assertions.assertThrows(FCWException.class, () -> processor.process("test", customScreeningCaseScreeningMessage));
    }
}
