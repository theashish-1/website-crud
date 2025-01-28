import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { ViewEmployeeComponent } from './view-employee/view-employee.component';
import { EditEmployeeComponent } from './edit-employee/edit-employee.component';
import { SideBarComponent } from './side-bar/side-bar.component';

export const routes: Routes = [
    {
        path : '' , component : LoginComponent
    },
    {
        path:'add', component : AddEmployeeComponent
    },
    {
        path:'view' ,component : ViewEmployeeComponent
    },
    {
        path:'edit/:id' , component : EditEmployeeComponent
    },
    {
        path :'sidebar',component:SideBarComponent
    }

    
];
