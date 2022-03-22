package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util.Converters.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDTO {

    private Integer id;
    @NotNull( message = "Name required" )
    private String name;
    private List<EmployeeDTO> employees;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public ProjectDTO( ProjectEntity projectEntity ) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.employees = listConverterEmployeeEntityToDto( projectEntity.getEmployees() );
        this.createdAt = projectEntity.getCreatedAt();
        this.updatedAt = projectEntity.getUpdatedAt();
    }

}
