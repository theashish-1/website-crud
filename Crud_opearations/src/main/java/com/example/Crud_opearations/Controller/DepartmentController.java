package com.example.Crud_opearations.Controller;

import com.example.Crud_opearations.DTO.DepartmentDTO;
import com.example.Crud_opearations.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDto){
        DepartmentDTO savedDepartment = departmentService.addDepartment(departmentDto);
        return ResponseEntity.ok(savedDepartment);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartments(){
        List<DepartmentDTO> department= departmentService.getDepartment();
        return ResponseEntity.ok(department);
    }
}
