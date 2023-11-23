package uz.pdp.govqueue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.govqueue.enums.RoleEnum;
import uz.pdp.govqueue.mapper.OperatorMapper;
import uz.pdp.govqueue.model.Operator;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.OperatorAddDTO;
import uz.pdp.govqueue.payload.OperatorDTO;
import uz.pdp.govqueue.repository.OperatorRepository;
import uz.pdp.govqueue.repository.UserRepository;
import uz.pdp.govqueue.utils.AppConstants;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;
    private final UserRepository userRepository;
    private final OperatorMapper operatorMapper;

    @Override
    public ApiResult<OperatorDTO> create(OperatorAddDTO operatorAddDTO) {
        //operator save
        String username = makeUsername();
        String password = "Root_123";
        User user = User.builder()
                .role(RoleEnum.OPERATOR)
                .password(password)
                .username(username)
                .build();

        userRepository.save(user);
        Operator operator = Operator.builder()
                .name(operatorAddDTO.getName())
                .status(operatorAddDTO.isStatus())
                .userId(user.getId())
                .build();
        operatorRepository.save(operator);
        OperatorDTO operatorDTO = operatorMapper.toOperatorDTO(operator);

        return new ApiResult<>(operatorDTO);
    }

    private String makeUsername() {
        Optional<User> lastOperatorOptional = userRepository.findFirstByUsernameStartingWithOrderByUsernameDesc(AppConstants.OPERATOR_USERNAME);

        String username;
        if (lastOperatorOptional.isEmpty())
            username = AppConstants.OPERATOR_USERNAME + "1";
        else {
            User user = lastOperatorOptional.get();
            int currNumber = Integer.parseInt(user.getUsername()
                    .substring(AppConstants.OPERATOR_USERNAME.length())) + 1;
            username = AppConstants.OPERATOR_USERNAME + currNumber;
        }
        return username;
    }
}
