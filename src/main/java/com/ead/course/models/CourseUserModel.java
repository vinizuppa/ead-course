package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor//Define para criar um construtor com todos argumentos.
@NoArgsConstructor//Define que terá casos onde teremos que utilizar um construtor sem argumentos.
@JsonInclude(JsonInclude.Include.NON_NULL)//Todas vezes que for serializar o objeto para JSON, será oculto objetos com valores nulos.
@Entity
@Table(name = "TB_COURSES_USERS")
public class CourseUserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID userId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//Posso ter vários registros de TB_USERS_COURSES para um unico User.
    private CourseModel course;
}
