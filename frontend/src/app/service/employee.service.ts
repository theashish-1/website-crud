import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8080/api/v1/employees';
  private addUrl = 'http://localhost:8080/api/v1/employees/addEmployee';
  private departmentUrl = 'http://localhost:8080/api/v1/department';
  constructor(private http : HttpClient) { }

  getAllEmployees() : Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  createEmployee(employeeData:any) : Observable<any>{
    return this.http.post(`${this.addUrl}`,employeeData)

  }

  deleteEmployees(employeeIds: number[]): Observable<any> {
    const deleteUrl = `${this.baseUrl}/delete`;
    return this.http.post<number[]>(deleteUrl, employeeIds);
  }


  getEmployeeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
  
  updateEmployee(id: number, employeeData: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, employeeData);
  }

  getAddDepartments() : Observable<any[]>{
    return this.http.get<any[]>(`${this.departmentUrl}`);
  }
  
}
