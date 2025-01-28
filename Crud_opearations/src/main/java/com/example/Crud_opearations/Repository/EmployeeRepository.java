package com.example.Crud_opearations.Repository;

import com.example.Crud_opearations.DTO.EmployeeDTO;
import com.example.Crud_opearations.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

}
