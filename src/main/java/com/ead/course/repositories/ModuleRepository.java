package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {
    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)//SELECT customizado.
    List<ModuleModel> findAllModulesIntoCurse(@Param("courseId") UUID courseId);//Essa consulta ira trazer todos modules de um determinado Course

    @Query(value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);//Retorna todos modulos de um determinado curso e determinado modulo.
}
