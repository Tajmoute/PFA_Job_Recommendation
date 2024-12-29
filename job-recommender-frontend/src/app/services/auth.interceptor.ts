import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  console.log('Intercepted request:', req); // Debugging
  console.log('Token:', token); // Debugging

  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Modified request with Authorization header:', clonedRequest); // Debugging
    return next(clonedRequest);
  }

  return next(req);
};
