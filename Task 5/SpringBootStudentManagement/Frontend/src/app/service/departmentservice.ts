import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({providedIn:'root'})
export class Departmentservice {
    constructor(private http:HttpClient){}

    //set bearer in header
    // getOpetionWithHeader(){
    //     const token = sessionStorage.getItem("token");
    //     return new HttpHeaders({
    //             'Authorization': `Bearer ${token}`
    //         })
    // }

    //Get All Departments
    getAllDepartments():Observable<HttpResponse<any[]>>
    {
        return this.http.get<any[]>(`${environment.apiUrl}/departments`,{observe:'response'});    
    }
    
    //add new Department
    addDepartment(departmentName:string) : Observable<HttpResponse<any>>{
        return this.http.post(`${environment.apiUrl}/departments`,{departmentName:departmentName},{observe:'response'});
    }

    //delete department
    deleteDepartment(deptId:number) : Observable<HttpResponse<any>>{
        return this.http.delete(`${environment.apiUrl}/departments/${deptId}`,{observe:'response'});
    }

    //update department
    updateDepartment(deptId:number,departmentName:string) : Observable<HttpResponse<any>>{
        return this.http.put(`${environment.apiUrl}/departments/${deptId}`,{departmentName:departmentName},{observe:'response'});
    }
}
