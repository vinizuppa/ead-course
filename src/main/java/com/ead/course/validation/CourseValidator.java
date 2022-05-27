package com.ead.course.validation;

import com.ead.course.configs.security.AuthenticationCurrentUserService;
import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationCurrentUserService authenticationCurrentUserService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {//Metodo que importa para validação.
        CourseDto courseDto = (CourseDto) o;//realiza um casting: transforma o object em couseDto.
        validator.validate(courseDto, errors);//Valida cada um dos atributos de courseDto. É a mesma coisa de inserirmos @Valid na requisição Post de CourseController.
        if (!errors.hasErrors()){//Verifica se não teve nenhum erro no CourseDto.
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors){
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (currentUserId.equals(userInstructor)){//Compara se o id que vem na requisição é igual ao id do usuário que está autenticado no momento.
            Optional<UserModel> userModelOptional = userService.findById(userInstructor);
            if (!userModelOptional.isPresent()){
                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
            }
            if (userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())){
                errors.rejectValue("userInstructor", "UserInstructorError", "user must be INSTRUCTOR or ADMIN");
            }
        } else {
            throw new AccessDeniedException("Forbidden");
        }

    }

}
