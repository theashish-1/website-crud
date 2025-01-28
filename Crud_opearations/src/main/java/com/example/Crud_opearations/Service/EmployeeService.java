package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.DTO.DepartmentDTO;
import com.example.Crud_opearations.DTO.EmployeeDTO;
import com.example.Crud_opearations.Entity.AddressEntity;
import com.example.Crud_opearations.Entity.DepartmentEntity;
import com.example.Crud_opearations.Entity.EmployeeEntity;
import com.example.Crud_opearations.Repository.AddressRepository;
import com.example.Crud_opearations.Repository.DepartmentRepository;
import com.example.Crud_opearations.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {

        DepartmentEntity department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(department);

        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
        savedEmployeeDTO.setFirstName(savedEmployee.getFirstName());
        savedEmployeeDTO.setLastName(savedEmployee.getLastName());
        savedEmployeeDTO.setDateOfBirth(savedEmployee.getDateOfBirth());
        savedEmployeeDTO.setEmail(savedEmployee.getEmail());
        savedEmployeeDTO.setSalary(savedEmployee.getSalary());
        savedEmployeeDTO.setDepartmentId(savedEmployee.getDepartment().getDepartmentId());  // Set the department ID

        return savedEmployeeDTO;
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        // Fetching from DB
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeEntity.getId());
        employeeDTO.setFirstName(employeeEntity.getFirstName());
        employeeDTO.setLastName(employeeEntity.getLastName());
        employeeDTO.setDateOfBirth(employeeEntity.getDateOfBirth());
        employeeDTO.setEmail(employeeEntity.getEmail());
        employeeDTO.setSalary(employeeEntity.getSalary());


        List<AddressEntity> address = addressRepository.findByEmployeeId(employeeEntity.getId());
       employeeDTO.setAddress1(address.get(0).getAddress());
       employeeDTO.setAddress2(address.get(1).getAddress());






        employeeDTO.setDepartmentId(employeeEntity.getDepartment().getDepartmentId());
        employeeDTO.setDepartmentName(employeeEntity.getDepartment().getName());



        return employeeDTO;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        for (DepartmentEntity department : departments) {
            DepartmentDTO dto = new DepartmentDTO();
            dto.setId(department.getDepartmentId());
            dto.setName(department.getName());
            departmentDTOList.add(dto);
        }

        return departmentDTOList;
    }


    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());

        // Set Department
        DepartmentEntity department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);





        List<AddressEntity> address = addressRepository.findByEmployeeId(employee.getId());
        AddressEntity address1 = new AddressEntity(employee, employeeDTO.getAddress1());
        AddressEntity address2 = new AddressEntity(employee, employeeDTO.getAddress2());
        if(!address.isEmpty()){
            address1.setId(address.get(0).getId());
            address2.setId(address.get(1).getId());
        }
        addressRepository.save(address1);
        addressRepository.save(address2);
//                .orElse(new AddressEntity(employee, employeeDTO.getAddress2()));
//        address2.setAddress(employeeDTO.getAddress2());
//        addressRepository.save(address2);

        // Saving the updated employee
        EmployeeEntity updatedEmployee = employeeRepository.save(employee);

        // Convert to EmployeeDTO
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setId(updatedEmployee.getId());
        updatedEmployeeDTO.setFirstName(updatedEmployee.getFirstName());
        updatedEmployeeDTO.setLastName(updatedEmployee.getLastName());
        updatedEmployeeDTO.setDateOfBirth(updatedEmployee.getDateOfBirth());
        updatedEmployeeDTO.setEmail(updatedEmployee.getEmail());
        updatedEmployeeDTO.setSalary(updatedEmployee.getSalary());
        updatedEmployeeDTO.setDepartmentId(updatedEmployee.getDepartment().getDepartmentId());
        updatedEmployeeDTO.setDepartmentName(updatedEmployee.getDepartment().getName());

        // Set Address1 and Address2
        updatedEmployeeDTO.setAddress1(address1.getAddress());
        updatedEmployeeDTO.setAddress2(address2.getAddress());

        return updatedEmployeeDTO;
    }



    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }


    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities.stream().map(employeeEntity -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employeeEntity.getId());
            employeeDTO.setFirstName(employeeEntity.getFirstName());
            employeeDTO.setLastName(employeeEntity.getLastName());
            employeeDTO.setDateOfBirth(employeeEntity.getDateOfBirth());
            employeeDTO.setEmail(employeeEntity.getEmail());
            employeeDTO.setSalary(employeeEntity.getSalary());
            employeeDTO.setDepartmentId(employeeEntity.getDepartment().getDepartmentId());
            employeeDTO.setDepartmentName(employeeEntity.getDepartment().getName()); // Populate departmentName
            //employeeDTO.setAddress1();

            return employeeDTO;
        }).collect(Collectors.toList());
    }

    public EmployeeEntity createEmployee(EmployeeDTO employeeDTO){
        System.out.println("Employee DTO Value is "+employeeDTO.getDepartmentId());
        System.out.println("Employee name is "+employeeDTO.getFirstName());
        if (employeeDTO.getDepartmentId() == null || employeeDTO.getDepartmentId()==0) {
            throw new IllegalArgumentException("Department id cannot be null or empty");
        }
//        DepartmentEntity departmentEntity = new DepartmentEntity();

        DepartmentEntity departmentEntity = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Department ID: " + employeeDTO.getDepartmentId()));

//        departmentEntity.setName(employeeDTO.getDepartmentName());
//        departmentEntity = departmentRepository.save(departmentEntity);

        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(departmentEntity);

        employee = employeeRepository.save(employee);

        AddressEntity address1 = new AddressEntity(employee,employeeDTO.getAddress1());
        AddressEntity address2 = new AddressEntity(employee,employeeDTO.getAddress2());

        addressRepository.save(address1);
        addressRepository.save(address2);

        return employee;

    }

    public void deleteEmployees(List<Integer> employeeIds) {
        for (Integer id : employeeIds) {

            employeeRepository.deleteById(id);
        }
    }
}