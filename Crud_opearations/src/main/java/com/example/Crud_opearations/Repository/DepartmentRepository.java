package com.example.Crud_opearations.Repository;

import com.example.Crud_opearations.Entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
