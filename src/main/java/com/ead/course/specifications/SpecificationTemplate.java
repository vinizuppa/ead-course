package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
    //Anotação que combina os filtros
    @And({
            @Spec(path = "courseLevel", spec = Equal.class),//Patch = parametro que será utilizado para filtrar, SPEC= tipo que vai ser o specification.
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)

    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @And({
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class)})
    public interface UserSpec extends Specification<UserModel>{}

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId){//Metodo que combina consultas: Modules de um determinado Course, com paginação e Specification.
        return (root, query, cb) -> {//Lambda. Utilizamos cryteria builder.
            query.distinct(true);//Definimos que essa query não terá valores duplicados.
            Root<ModuleModel> module = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");//Extraimos a coleção de modulos que estão presentes dentro de um determinado curso.
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules));//Construindo criteria builder para fazer consulta.
        };
    }
    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {//Lambda. Utilizamos cryteria builder.
            query.distinct(true);//Definimos que essa query não terá valores duplicados.
            Root<LessonModel> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");//Extraimos a coleção de lessons que estão presentes dentro de um determinado module.
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons));//Construindo criteria builder para fazer consulta.
        };
    }

    public static Specification<UserModel> userCourseId(final UUID courseId){//Metodo que combina consultas: users de um determinado Course, com paginação e Specification.
        return (root, query, cb) -> {//Lambda. Utilizamos cryteria builder.
            query.distinct(true);//Definimos que essa query não terá valores duplicados.
            Root<UserModel> user = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<UserModel>> coursesUsers = course.get("users");//Extraimos a coleção de users que estão presentes dentro de um determinado curso.
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, coursesUsers));//Construindo criteria builder para fazer consulta.
        };
    }

    public static Specification<CourseModel> courseUserId(final UUID userId){
        return (root, query, cb) -> {//Lambda. Utilizamos cryteria builder.
            query.distinct(true);//Definimos que essa query não terá valores duplicados.
            Root<CourseModel> course = root;
            Root<UserModel> user = query.from(UserModel.class);
            Expression<Collection<CourseModel>> usersCourses = user.get("courses");//Extraimos a coleção de courses que estão presentes dentro de um determinado user.
            return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(course, usersCourses));//Construindo criteria builder para fazer consulta.
        };
    }


}
