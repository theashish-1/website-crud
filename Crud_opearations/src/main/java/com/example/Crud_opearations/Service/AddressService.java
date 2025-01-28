package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.DTO.AddressDTO;
import com.example.Crud_opearations.Entity.AddressEntity;
import com.example.Crud_opearations.Entity.EmployeeEntity;
import com.example.Crud_opearations.Repository.AddressRepository;
import com.example.Crud_opearations.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AddressDTO getAddressById(int id) {
        return addressRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        EmployeeEntity employee = employeeRepository.findById(addressDTO.getEmployeeId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid employee ID"));

        AddressEntity entity = new AddressEntity(employee ,addressDTO.getAddress());
        entity.setEmployee(employee);
        entity.setAddress(addressDTO.getAddress());

        return convertToDTO(addressRepository.save(entity));
    }

    public AddressDTO updateAddress(int id, AddressDTO addressDTO) {
        AddressEntity entity = addressRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Address not found"));

        EmployeeEntity employee = employeeRepository.findById(addressDTO.getEmployeeId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid employee ID"));

        entity.setEmployee(employee);
        entity.setAddress(addressDTO.getAddress());

        return convertToDTO(addressRepository.save(entity));
    }

    public void deleteAddress(int id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO convertToDTO(AddressEntity entity) {
        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setAddress(entity.getAddress());
        return dto;
    }

}
