package com.ead.course.validation;

import com.ead.course.dtos.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

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


//        ResponseEntity<UserDto> responseUserInstructor;
//        try {
//            responseUserInstructor = authUserClient.getOneUserById(userInstructor);
//            if (responseUserInstructor.getBody().getUserType().equals(UserType.STUDENT)){//Verifica se usuário é do tipo STUDENT.
//                errors.rejectValue("userInstructor", "UserInstructorError", "user must be INSTRUCTOR or ADMIN");
//            }
//        } catch (HttpStatusCodeException e) {//Verifica a exeção
//            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){//Caso a exceção for NOT_FOUND:
//                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
//            }
//        }
    }

}
