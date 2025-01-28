
import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { SideBarComponent } from '../side-bar/side-bar.component';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { AuthService } from '../service/auth.service';
import { EmployeeService } from '../service/employee.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AddEmployeeComponent } from '../add-employee/add-employee.component';
import { EditEmployeeComponent } from '../edit-employee/edit-employee.component';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
// import { MatTableModule } from '@angular/material';
@Component({
  selector: 'app-view-employee',
  imports: [SideBarComponent, NavBarComponent, FormsModule, CommonModule, MatDialogModule, AddEmployeeComponent,EditEmployeeComponent,PopUpComponent,MatPaginatorModule],
  templateUrl: './view-employee.component.html',
  styleUrl: './view-employee.component.css'
})
export class ViewEmployeeComponent implements OnInit {

  employees: any[] = [];
  isAdmin: boolean = false;
  selectedEmployee: any = null;
  isListVisible: boolean = true;  
  selectedEmployeeId: number | null = null;
  router: Router = inject(Router);


  filteredEmployees: any[] = [];
  searchQuery: string = '';
  

 // Pagination variables
//  filteredEmployees: any[] = [];
//  searchQuery: string = '';
    pageIndex: number = 0;
    pageSize: number = 10;
    totalEmployees: number = 0;      

    

    applyPagination(): void {
      const startIndex = this.pageIndex * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.employees.length);
      this.filteredEmployees = this.employees.slice(startIndex, endIndex); // update employees to show on ui
    }
    
  
    onPaginatorChange(event: any): void {
      this.pageIndex = event.pageIndex; 
      this.pageSize = event.pageSize; 
      this.applyPagination(); // Apply pagination to update filteredEmployees
    }
    

  constructor(private authService: AuthService, private employeeService: EmployeeService, private dialog: MatDialog) {}

  ngOnInit(): void {
    if(localStorage.getItem('authToken')){
      this.isAdmin = this.authService.getAdminStatus();
      console.log('The admin value is ', this.isAdmin);
      this.loadEmployees();
    }else{
      this.router.navigate([''])
    }
    
  }


  filterEmployees() {
    const query = this.searchQuery.trim().toLowerCase();
    if (query) {
      this.filteredEmployees = this.employees.filter(
        employee =>
          employee.firstName.toLowerCase().includes(query) ||
          employee.lastName.toLowerCase().includes(query)
      );
    } else {
      this.filteredEmployees = [...this.employees];
    }
  }
  addEmployee() {
    if (this.isAdmin) {
      this.isListVisible = false;
      this.selectedEmployeeId = null;
    }
  }

  onCancelAddEmployee() {
    this.isListVisible = true;
    this.selectedEmployeeId = null; 
  }

  onEmployeeAdded(employee: any) {
    this.loadEmployees();
    this.isListVisible = true;
  }

  onEmployeeEdited(employee : any){
    this.loadEmployees();
    this.isListVisible = true;
  }

  loadEmployees() {
    this.employeeService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data.map((employee) => ({ ...employee, selected: false })).sort((a, b) => b.id - a.id);
        this.filteredEmployees = [...this.employees];
        this.totalEmployees = this.employees.length; // Set total employees count
        this.pageIndex = 0; // Reset to the first page
        this.applyPagination(); // Apply pagination
        console.log('Employee data is ', this.employees);
        console.log("filtered employees is ",this.filteredEmployees);
        
      },
      error: (err) => {
        console.log('Error while fetching data ', err);
      }
    });
  }

  toggleSelection(employee: any) {
    console.log(`Employee ${employee.id} selected status: ${employee.selected}`);
  }

  // editEmployee(employeeId: number) {
  //   this.isListVisible = false;
  //   this.selectedEmployeeId = employeeId;
  // }

  editEmployee(employeeId: number , event: Event): void {
    event.stopPropagation();
    this.selectedEmployeeId = employeeId;  
    this.isListVisible = false;  
  }

  deleteEmployee(employeeId: number , event : Event) {
    event.stopPropagation();
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '300px',
        data: { message: 'Are you sure you want to delete this employee?' }
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.employeeService.deleteEmployees([employeeId]).subscribe({
            next: () => this.loadEmployees(),
            error: (err: any) => console.log('Error while deleting employee', err)
          });
        }
      });
    }
  }

  selectAllEmployees(event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    this.employees.forEach((employee) => (employee.selected = isChecked));
  }

  onCancelEditEmployee(){
    
    this.isListVisible = true;
  }

  deleteEmployees() {
    const selectedEmployeeIds = this.employees.filter((employee) => employee.selected).map((employee) => employee.id);
    if(selectedEmployeeIds.length ===0 && this.employees.length>0){
      const dialogRef = this.dialog.open(ConfirmDialogComponent,{
        width : '250px',
        data:{message : 'Please select atleast one employee'}
      })
    }

    if(this.employees.length===0){
      const dialogRef = this.dialog.open(ConfirmDialogComponent,{
        width:'250px',
        data:{message: 'No items found'}
      })
    }

    if (this.isAdmin && selectedEmployeeIds.length > 0) {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '250px',
        data: { message: 'Are you sure you want to delete selected employees?' }
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.employeeService.deleteEmployees(selectedEmployeeIds).subscribe({
            next: () => this.loadEmployees(),
            error: (err) => console.log('Error while deleting employees', err)
          });
        }
      });
    }
  }


  viewEmployeeDetails(employee:any , event: Event){
    //this.selectedEmployee = employee;

    if ((event.target as HTMLElement).tagName === 'INPUT') {
      return; 
    }
    this.selectedEmployee = employee;
    console.log("Selected employees are ",this.selectedEmployee);
    
    // Open the MatDialog with your component
    this.dialog.open(PopUpComponent, {
      data: { employee: this.selectedEmployee },
      width: '600px',  
      disableClose: false,  
    });
  }
  selectAllEmployee(arg0: string, selectAllEmployee: any) {
    throw new Error('Method not implemented.');
  }
  closeModal() {
    this.selectedEmployee = null;
  }
}

  


