

import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../service/employee.service';
import { CommonModule, Location } from '@angular/common';

@Component({
  selector: 'app-edit-employee',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.css']
})
export class EditEmployeeComponent implements OnInit {

  @Input() employeeId: any;  // Using @Input() to receive data from the parent component
  @Input() isListVisibleEdit:any;
  //@Input() cancelEdit:any;

  @Output() employeeEdited = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<any>();
  maxDate: any;
  employeeForm: FormGroup;
  department: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private location: Location
  ) {
    this.employeeForm = this.fb.group({
      firstName: ['',[Validators.required, Validators.pattern('^[A-Za-z ]+$')]],
      lastName: ['',[Validators.required, Validators.pattern('^[A-Za-z ]+$')]],
      email: ['', [Validators.required, Validators.email, Validators.pattern('.+\\.com$')]],
      salary: ['',[Validators.required, Validators.min(0)]],
      departmentId: ['',[Validators.required]],
      dateOfBirth: ['',[Validators.required, this.ageValidator(18)]],
      address1: ['',[Validators.required]],
      address2: ['']
    });
  }

  ngOnInit(): void {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Add leading zero
    const day = String(today.getDate()).padStart(2, '0'); // Add leading zero
    this.maxDate = `${year}-${month}-${day}`;
    // if the employee data is passed through @Input() show  the form
    this.loadDepartments();
    if (this.employeeId) {
      console.log(this.employeeId);
      
      this.loadEmployeeDetails();
    }

    // Hardcoded department data could be fetched from an API if needed
    // this.department = [
    //   { name: "HR", value: 1 },
    //   { name: "Health Care", value: 2 },
    //   { name: "Telecom", value: 5 },
    //   { name: "SPE", value: 4 }
    // ];
  }

  loadDepartments(){
    this.employeeService.getAddDepartments().subscribe({
      next : (data)=> this.department = data.map( d=>({name:d.name , value : d.id})),
      error : (err) => console.log("Error while loading employees",err)
      
    })
  }

  loadEmployeeDetails() {
    // const employeeId = +(this.route.snapshot.paramMap.get('id') ?? 0);
    const employeeId=this.employeeId
    
    this.employeeService.getEmployeeById(employeeId).subscribe({
      next: (employee) => this.employeeForm.patchValue(employee),
      error: (err) => console.log('Error loading employee details', err)
    });
  }

 


  saveEmployee() {
    const updatedEmployee = this.employeeForm.value;
  
    
    const employeeId = this.employeeId;  // This will use the @Input value
  
    if (!employeeId) {
      console.error('Employee ID is missing');
      return;
    }
  
    console.log("updated employee is ", updatedEmployee);
  
    // Update employee data via service
    this.employeeService.updateEmployee(employeeId, updatedEmployee).subscribe({
      next: () => {
        console.log('Employee updated successfully');
        // Navigate back to the employee list or view component after successful update
        //this.router.navigate(['/view']);
        // this.isListVisibleEdit=true;
        this.employeeEdited.emit();

      },
      error: (err) => console.log('Error updating employee', err)
    });
  }
  
  cancelEdit() {
    this.router.navigate(['/view']);
  }

  // back() {
  //   this.location.back();
  // }

  onCancelEditEmployee() {
    console.log("Going back to the previous page");
    
    this.cancel.emit(); // Emit cancel event to parent component
    
    
    this.router.navigate(['/view']);
  }

  ageValidator(minAge: number) {
    return (control: AbstractControl): ValidationErrors | null => {
      const dob = new Date(control.value);
      const today = new Date();
      let age = today.getFullYear() - dob.getFullYear();
      const monthDifference = today.getMonth() - dob.getMonth();
      const dayDifference = today.getDate() - dob.getDate();

      if (monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)) {
        age--;
      }

      return age >= minAge ? null : { ageInvalid: true };
    };
  }
}
