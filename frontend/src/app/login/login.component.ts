import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  isValid : boolean = true;
  employeeObj: any = {
    userName: '',
    password: ''
  };
  router : Router = inject(Router);

  constructor(private authService: AuthService) {
    localStorage.clear();
  }

  onSubmit() {
    const credentials = {
      email: this.employeeObj.userName,
      password: this.employeeObj.password,
    };
  
    this.authService.login(credentials).subscribe(
      (response) => {
        //alert("Login Sucessfull");
        this.isValid = true;
        const token = "abcdef";
        localStorage.setItem('authToken', token);

        console.log(this.isValid);
        
        console.log(response);
        this.router.navigate(['/view']);
      },
      (error) => {
        this.isValid = false;
        //alert("Invalid credentials");
        console.log(error);
      }
    );
  }
  

}
