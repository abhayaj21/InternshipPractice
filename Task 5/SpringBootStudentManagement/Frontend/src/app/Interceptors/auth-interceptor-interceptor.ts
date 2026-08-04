import { HttpClient, HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { BehaviorSubject, catchError, filter, switchMap, take, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

// Keep state tracking variables
let isRefreshing = false;
const refreshTokenSubject = new BehaviorSubject<string | null>(null);

export const authInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const http = inject(HttpClient);
  const token = sessionStorage.getItem("token");
  let authreq = req;
  
  if (token) {
    authreq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(authreq).pipe(
    catchError((error: HttpErrorResponse) => {
      // FIX 1: Detect if this 403 error came from the refresh API endpoint itself
      const isRefreshRequest = req.url.includes('/auth/refreshToken');

      // FIX 2: Only attempt refresh if it is a 403 error AND NOT a failed refresh attempt
      if (error.status === 403 && token && !isRefreshRequest) {
        
        if (!isRefreshing) {
          isRefreshing = true;
          refreshTokenSubject.next(null); // Reset tracking subject

          const refreshToken = sessionStorage.getItem("refreshToken");

          // FIX 3: Pass along the refresh token payload exactly as your Spring Boot API requires it
          return http.post<any>(`${environment.apiUrl}/auth/refreshToken`, { refreshToken: refreshToken })
          .pipe(
            switchMap((res) => {
              isRefreshing = false;
              
              // FIX 4: Double-check the exact field name your Spring Boot returns (e.g., res.accessToken or res.newAccessToken)
              const newJwt = res.newAccessToken;
              
              sessionStorage.setItem("token", newJwt);
              refreshTokenSubject.next(newJwt);

              const retryReq = req.clone({
                setHeaders: { Authorization: `Bearer ${newJwt}` }
              });
              return next(retryReq);
            }),
            catchError((refreshError) => {
              // FIX 5: If the refresh token itself fails, clean up state completely and redirect to login
              isRefreshing = false;
              refreshTokenSubject.next(null);
              sessionStorage.clear();
              window.history.pushState({}, '', '/');
              window.location.reload();
              
              return throwError(() => refreshError);
            })
          );
        } else {
          // If a refresh is already in progress, queue subsequent requests until it resolves
          return refreshTokenSubject.pipe(
            filter(newToken => newToken !== null),
            take(1),
            switchMap((newJwt) => {
              const retryReq = req.clone({
                setHeaders: { Authorization: `Bearer ${newJwt}` }
              });
              return next(retryReq);
            })
          );
        }
      }
      
      // Return standard errors if conditions are not met
      return throwError(() => error);
    })
  );
};
