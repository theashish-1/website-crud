import { CommonModule } from '@angular/common';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { EmployeeService } from '../service/employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-employee',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,FormsModule],
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {
  employeeForm!: FormGroup;
  maxDate: any;
  departmentList: any[] = [];
  emailInput: string = '';
  errorMessage: string = '';
  namePattern: string = '^[A-Za-z ]+$';
  // Output event to notify parent when an employee is added
  @Output() employeeAdded = new EventEmitter<any>();
  // Output event to notify parent when the form is canceled
  @Output() cancel = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit() {
    // Initialize form group
    this.employeeForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.pattern('^[A-Za-z ]+$')]],
      lastName: ['', [Validators.required, Validators.pattern('^[A-Za-z ]+$')]],
      email: ['', [Validators.required, Validators.email, Validators.pattern('.+\\.com$')]],
      salary: ['', [Validators.required, Validators.min(0)]],
      departmentId: ['', [Validators.required]],
      dateOfBirth: ['', [Validators.required , this.ageValidator(18)]],
      address1: ['', [Validators.required]],
      address2: [''],
    });

    // Setting the maxDate for the DOB input
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    this.maxDate = `${year}-${month}-${day}`;

    // Fetch departments list from the service
    this.employeeService.getAddDepartments().subscribe(
      (response) => {
        this.departmentList = response;
        console.log('Departments fetched successfully', this.departmentList);
      },
      (error) => {
        console.error('Error fetching departments', error);
      }
    );
  }

  // Submit form method
  onSubmit() {
    if (this.employeeForm.valid) {
      console.log('Form values:', this.employeeForm.value);
      this.employeeService.createEmployee(this.employeeForm.value).subscribe(
        (response) => {
          console.log('Employee added successfully!', response);
          this.employeeAdded.emit(response); 
        },
        (error) => {
          alert('Error occurred: ' + error);
          console.error('Error while adding employee:', error);
        }
      );
    }
  }

  //navigating to the employee list
  onCancelAddEmployee() {
    this.cancel.emit(); // return to parent component
    this.router.navigate(['/view']);
  }


  validateEmail(){
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    console.log("running ");
    

    if (emailPattern.test(this.emailInput)) {
      this.errorMessage = '';
    } else {
      this.errorMessage = 'Invalid email address';
    }
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
