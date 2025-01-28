import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { EmployeeService } from '../service/employee.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-pop-up',
  imports: [CommonModule],
  templateUrl: './pop-up.component.html',
  styleUrl: './pop-up.component.css'
})
export class PopUpComponent implements OnInit {
  date:any;
  @Input() employee : any;
  @Output() closePopUp = new EventEmitter<void>();

  ngOnInit(){
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    this.date = `${day}-${month}-${year}`;

  }


  constructor(@Inject(MAT_DIALOG_DATA) public data: any , private dialogRef: MatDialogRef<PopUpComponent>) {
    console.log("Employees are ",this.data.employee); 
  }
 
  closeDialog(): void {
    this.dialogRef.close();
  }
}
