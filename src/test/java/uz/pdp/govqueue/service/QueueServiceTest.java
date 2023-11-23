package uz.pdp.govqueue.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.model.Queue;
import uz.pdp.govqueue.payload.AddQueueDTO;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.QueueForPrintDTO;
import uz.pdp.govqueue.repository.GovServiceRepository;
import uz.pdp.govqueue.repository.OperatorRepository;
import uz.pdp.govqueue.repository.QueueRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
    QueueService queueService;
    @Mock
    QueueRepository queueRepository;
    @Mock
    GovServiceRepository govServiceRepository;
    @Mock
    OperatorRepository operatorRepository;

    @BeforeEach
    void setUp() {
        queueService=new QueueServiceImpl(queueRepository,govServiceRepository,operatorRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSuccessTest() {
        AddQueueDTO addQueueDTO = new AddQueueDTO();
        addQueueDTO.setServiceId(1);

        GovService govService = new GovService();
        govService.setId(1);
        govService.setStatus(true);
        govService.setFirstLetter('A');
        when(govServiceRepository.findById(anyInt())).thenReturn(Optional.of(govService));

        when(queueRepository.countByCreatedAtBetweenAndStatusInAndNumberStartingWith(any(), any(),any(),anyString())).thenReturn(0);
        when(queueRepository.save(any())).thenReturn(new Queue());

        long avgYesterdayByFirstLetterMock=12;
        when(queueRepository.getAvgYesterdayByFirstLetter(any(),any(),anyString())).thenReturn(avgYesterdayByFirstLetterMock);
        long     operatorCountMock = 5;
        when(operatorRepository.countAllByStatusTrueAndServiceIncludes(anyString())).thenReturn(operatorCountMock);

        ApiResult<QueueForPrintDTO> result = queueService.create(addQueueDTO);

        assertNotNull(result.getData());
        assertNotNull(result.getData().getCreatedAt());
        assertNotNull(result.getData().getRoundTime());
        assertNotNull(result.getData().getServiceName());
        assertNotNull(result.getData().getNumber());

    }

    @Test
    void forBoard() {
    }

    @Test
    void callQueue() {
    }
}