package com.example.Crud_opearations.Repository;

import com.example.Crud_opearations.Entity.AddressEntity;
import com.example.Crud_opearations.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Integer> {
    Optional<AddressEntity> findByEmployeeAndAddress(EmployeeEntity employee, String address);
    List<AddressEntity> findByEmployeeId(Integer id);
}
