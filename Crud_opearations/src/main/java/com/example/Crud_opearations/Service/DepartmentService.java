package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.DTO.DepartmentDTO;
import com.example.Crud_opearations.Entity.DepartmentEntity;
import com.example.Crud_opearations.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public DepartmentDTO addDepartment(DepartmentDTO departmentDto) {
        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentId(departmentDto.getId());
        department.setName(departmentDto.getName());

        DepartmentEntity departmentEntity = departmentRepository.save(department);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(departmentEntity.getDepartmentId());
        departmentDTO.setName(departmentEntity.getName());

        return departmentDTO;


    }


    public List<DepartmentDTO> getDepartment() {
        List<DepartmentEntity> departments = departmentRepository.findAll();
        System.out.println("value of departments is "+departments);

        List<DepartmentDTO> allDeparments = new ArrayList<>();

        for (DepartmentEntity department : departments){
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getDepartmentId());
            departmentDTO.setName(department.getName());
            allDeparments.add(departmentDTO);
        }
        return allDeparments;


    }
}
