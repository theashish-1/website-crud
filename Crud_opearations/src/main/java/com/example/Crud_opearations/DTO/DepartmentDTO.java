package com.example.Crud_opearations.DTO;

import jakarta.persistence.Id;

import javax.naming.Name;

public class DepartmentDTO {
    private Integer Id;
    private String Name;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
