package uz.pdp.govqueue.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.pdp.govqueue.enums.RoleEnum;
import uz.pdp.govqueue.mapper.OperatorMapper;
import uz.pdp.govqueue.model.Operator;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.OperatorAddDTO;
import uz.pdp.govqueue.payload.OperatorDTO;
import uz.pdp.govqueue.repository.OperatorRepository;
import uz.pdp.govqueue.repository.UserRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {
    OperatorService operatorService;
    @Mock
    OperatorRepository operatorRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    OperatorMapper operatorMapper;

    @BeforeEach
    void setUp() {
        operatorService = new OperatorServiceImpl(operatorRepository, userRepository, operatorMapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Operator service testlandi")
    void createForSuccess() {
        OperatorAddDTO operatorAddDTO = new OperatorAddDTO("TestOperator", true, List.of(1, 2));
        User mockUser = User.builder()
                .id(1)
                .role(RoleEnum.OPERATOR)
                .password("123")
                .username("Username")
                .build();
        Operator mockOperator = Operator.builder()
                .id(1)
                .name("TestOperator")
                .status(true)
                .userId(mockUser.getId())
                .build();

        OperatorDTO mockOperatorDTO = new OperatorDTO();
        mockOperatorDTO.setId(mockOperator.getId());
        mockOperatorDTO.setName(mockOperator.getName());
        mockOperatorDTO.setStatus(mockOperator.isStatus());


        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(operatorRepository.save(any(Operator.class))).thenReturn(mockOperator);
        when(operatorMapper.toOperatorDTO(any(Operator.class))).thenReturn(mockOperatorDTO);

        ApiResult<OperatorDTO> result = operatorService.create(operatorAddDTO);
        assertEquals("TestOperator", result.getData().getName());
        assertTrue(result.getData().isStatus());

        verify(userRepository, atLeast(1)).save(any());
        verify(operatorRepository, atLeast(1)).save(any());
        verify(operatorMapper, atLeast(1)).toOperatorDTO(any());
    }


    @Test
    public void testMakeUsername() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User existingUser = User.builder()
                .username("operator1")
                .build();
        when(userRepository.findFirstByUsernameStartingWithOrderByUsernameDesc(anyString()))
                .thenReturn(Optional.of(existingUser));
        Method makeUsernameMethod = OperatorServiceImpl.class.getDeclaredMethod("makeUsername");
        makeUsernameMethod.setAccessible(true);

        String result = (String) makeUsernameMethod.invoke(operatorService);

        verify(userRepository, atLeast(1)).findFirstByUsernameStartingWithOrderByUsernameDesc(any());

        assertEquals("operator2", result);
    }
}