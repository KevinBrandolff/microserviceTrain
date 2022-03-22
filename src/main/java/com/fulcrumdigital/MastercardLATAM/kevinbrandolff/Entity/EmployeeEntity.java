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
@Entity( name = "kevinEmployeeEntity")
@Table( name = "employee_kevin")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false )
    private String name;

    @Column( unique = true, nullable = false )
    private String register;

    private Integer age;

    @Column( nullable = false )
    private String address;

    @Column( nullable = false )
    private Double salary;

    @Column( unique = true, nullable = false )
    private String email;

    @Column( nullable = false )
    private String department;

    @ManyToMany
    @Cascade( { MERGE, PERSIST, REFRESH } )
    @JsonIgnore
    @JoinTable( name = "employee_x_project_kevin",
            joinColumns = @JoinColumn( name = "id_employee" ),
            inverseJoinColumns = @JoinColumn( name = "id_project" ) )
    private List<ProjectEntity> projects;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(register, that.register) && Objects.equals(age, that.age) && Objects.equals(address, that.address) && Objects.equals(salary, that.salary) && Objects.equals(email, that.email) && Objects.equals(department, that.department);
    }


}
