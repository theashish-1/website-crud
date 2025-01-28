import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';
  private isAdmin: boolean = false;

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(`${this.baseUrl}/login`, credentials, { headers }).pipe(
      tap((response: any) => {
        console.log("response from backend is ",response);
        
        this.isAdmin = response.admin; 
        console.log("the admin value is ",this.isAdmin)
      })
    );
    
  }

  getAdminStatus(): boolean {
    return this.isAdmin;
  }
}
