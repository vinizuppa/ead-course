package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//Todas vezes que for serializar o objeto para JSON, será oculto objetos com valores nulos.
@Entity
@Table(name = "TB_MODULES")
public class ModuleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId; //Identificador para não causar conflitos em sistemas distribuidos(bancos distribuidos). É um ID temporal, não existe possibilidade de ser gerado 2 Ids iguais.
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 250)
    private String description;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")//Retorna data no formato desejado
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Define o tipo de acesso a esse atributo.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseModel course;//Define a relação: Vários MODULOS podem pertencer a um CURSO

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Define o tipo de acesso a esse atributo.
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) //Define a maneira como ira ser buscado os dados no banco
    private Set<LessonModel> lessons;
}
