package com.example.Crud_opearations.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer departmentId;

    @Column(name = "Name", nullable = false)
    private String name;

    // Getters and Setters
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
