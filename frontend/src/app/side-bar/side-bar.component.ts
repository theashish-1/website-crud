import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar',
  imports: [CommonModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {
  
  constructor(private router : Router){}
  activeButton : string = '';
  @Input() isAdmin:any;
  setActiveButton(button : string){
    this.activeButton = button;
    // this.router.navigate(['/login'])
  }

  logout(){
    localStorage.clear();
    this.router.navigate([''])
  }

  addEmployee(){
    this.router.navigate(['/add'])
  }

}
