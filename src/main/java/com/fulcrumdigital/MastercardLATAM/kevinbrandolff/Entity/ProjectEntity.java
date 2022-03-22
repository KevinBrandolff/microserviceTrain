package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "kevinProjectEntity")
@Table( name = "project_kevin")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @Cascade( { REFRESH, PERSIST } ) //NO MERGE CAUSE IS GIVING TROUBLE WHEN TRY UPDATE A EMPLOYEE UPDATING SOME FILED OF SOME PROJECT
    @JoinTable( name = "employee_x_project_kevin",
            joinColumns = @JoinColumn( name = "id_project" ),
            inverseJoinColumns = @JoinColumn( name = "id_employee" ) )
    @JsonIgnore
    private List<EmployeeEntity> employees;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

}
