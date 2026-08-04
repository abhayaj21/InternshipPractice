import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable} from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
    private loggedIn = false;
    constructor(private http:HttpClient){}

    login(user:any){
        return this.http.post(`${environment.apiUrl}/auth/login`,user,{observe:'response',});
    }

    register(user:any)
    {
        return this.http.post(`${environment.apiUrl}/auth/user-register`,user,{observe:'response'});
    }

    setLoginStatus(status:boolean) : void
    {
        this.loggedIn = status;
        sessionStorage.setItem('isLogin',status ? 'true':'false');
    }

    setJwtToken(token:string) : void{
        sessionStorage.setItem("token",token);
    }

    setRefereshToken(token:string) : void{
        sessionStorage.setItem("refreshToken",token);
    }

    getLoginStatus():boolean {
        return sessionStorage.getItem('isLogin') === 'true';
    }
}
