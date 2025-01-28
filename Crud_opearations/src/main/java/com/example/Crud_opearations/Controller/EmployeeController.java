package com.example.Crud_opearations.Controller;

import com.example.Crud_opearations.DTO.AuthResponse;
import com.example.Crud_opearations.DTO.DeleteResponse;
import com.example.Crud_opearations.DTO.DepartmentDTO;
import com.example.Crud_opearations.DTO.EmployeeDTO;
import com.example.Crud_opearations.Entity.EmployeeEntity;
import com.example.Crud_opearations.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        System.out.print("The id from ui is"+id);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("employee deleted sucessfully ");
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployess(){
        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.addEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping("/addEmployee")
    public EmployeeEntity createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.createEmployee(employeeDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<DeleteResponse> deleteEmployees(@RequestBody List<Integer> ids) {
        System.out.println("ids are "+ids);
        employeeService.deleteEmployees(ids);
        return ResponseEntity.ok(new DeleteResponse("Deleted Successfully"));
    }



}