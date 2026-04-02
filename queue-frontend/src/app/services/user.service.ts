import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { User } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  // Fetch faculty users. If search is blank/undefined, backend returns all faculty.
  getFaculty(search?: string): Observable<User[]> {
    let params = new HttpParams();

    // Only send the param if it has useful content. Keeps URLs clean.
    if (search && search.trim().length > 0) {
      params = params.set('search', search.trim());
    }

    return this.http.get<User[]>(`${this.apiUrl}/users/faculty`, { params });
  }
}
