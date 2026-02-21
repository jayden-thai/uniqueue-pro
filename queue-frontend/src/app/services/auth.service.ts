import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface User {
  id?: number;
  email: string;
  password?: string;
  role: 'STUDENT' | 'FACULTY' | undefined;
  universityId: string;
  name: string;
  department?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;
  private currentUserSubject = new BehaviorSubject<User | null>(null);

  currentUser$ = this.currentUserSubject.asObservable();

  login(loginReq: LoginRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users/login`, loginReq).pipe(
      tap(user => {
        this.currentUserSubject.next(user);
      })
    );
  }

  logout() {
    this.currentUserSubject.next(null);
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users/register`, user).pipe(
      tap(savedUser => {
        this.currentUserSubject.next(savedUser);
      })
    );
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.getValue();
  }

  isLoggedIn(): boolean {
    return this.getCurrentUser() !== null;
  }
}
