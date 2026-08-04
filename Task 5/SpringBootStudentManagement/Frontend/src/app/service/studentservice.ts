import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class Studentservice {
    constructor(private http:HttpClient){}

    //set bearer in header
    // getOpetionWithHeader(){
    //     const token = sessionStorage.getItem("token");
    //     return new HttpHeaders({
    //             'Authorization': `Bearer ${token}`
    //         })
    // }

    //save Student Api call
    saveStudent(deptId:number,student:any):Observable<HttpResponse<any>>{
        return this.http.post(`${environment.apiUrl}/students/${deptId}`,student,{observe:'response'});
    }

    //get All students
    fetchAllStudents() : Observable<HttpResponse<any[]>>{
        return this.http.get<any[]>(`${environment.apiUrl}/students`,{observe:'response'});
    }

    //delete student
    deleteStudent(roll:number): Observable<HttpResponse<any>>{
        return this.http.delete(`${environment.apiUrl}/students/delete/${roll}`,{observe:'response'});
    }

    //update Student
    updateStudent(roll:number,student:any): Observable<HttpResponse<any>>{
        return this.http.put(`${environment.apiUrl}/students/update/${roll}`,student,{observe:'response'});
    }

    //apply pagination
    applyPagination(page:number,size:number) : Observable<HttpResponse<any[]>>{
        let params = new HttpParams()
        .set('page',page)
        .set('size',size);
        return this.http.get<any[]>(`${environment.apiUrl}/students/page`,{params,observe:'response'});
    }

    //sorting students
    sortingStudent(direction:string,sortBy:string) : Observable<HttpResponse<any[]>>{
        const params = new HttpParams()
        .set('direction',direction)
        .set('sortBy',sortBy);

        return this.http.get<any[]>(`${environment.apiUrl}/students/sort`,{params:params,observe:'response'});
    }

    //search student
    searchStudent(department:string) : Observable<HttpResponse<any[]>>{
        const params = new HttpParams()
        .set('departmentName',department.toUpperCase());
        return this.http.get<any[]>(`${environment.apiUrl}/students/search`,{params,observe:'response'})
    }
}
