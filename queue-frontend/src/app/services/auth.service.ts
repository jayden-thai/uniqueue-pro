import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface User {
  id?: number;
  email: string;
  password?: string;
  role: 'USER' | 'FACULTY';
  universityId: string;
  name: string;
  department?: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;
  private currentUserSubject = new BehaviorSubject<User | null>(null);

  currentUser$ = this.currentUserSubject.asObservable();

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(user => {
        this.currentUserSubject.next(user);
      })
    );
  }

  logout() {
    this.currentUserSubject.next(null);
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user).pipe(
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
