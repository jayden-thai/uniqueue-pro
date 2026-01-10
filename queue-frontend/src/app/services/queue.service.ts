import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { AuthService } from './auth.service';

export interface QueueEntry {
  id: number;
  joinedAt: string;
  active: boolean;
  user: {
    id: number;
    name: string;
    universityId: string;
    email: string;
    department?: string;
    role: 'STUDENT' | 'FACULTY';
  }
  
}

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;
  private auth = inject(AuthService);

  getQueue(): Observable<QueueEntry[]>{
    return this.http.get<QueueEntry[]>(`${this.apiUrl}/queue`);
  }

  joinQueue(): Observable<QueueEntry> {
    const user = this.auth.getCurrentUser();
    if (!user?.id) throw new Error('No logged-in user (missing id)'); 
    
    return this.http.post<QueueEntry>(`${this.apiUrl}/queue/join/${user.id}`, {});
  }

  leaveQueue(): Observable<void> {
    const user = this.auth.getCurrentUser();
    if (!user?.id) throw new Error('No logged-in user (missing id).');

    return this.http.post<void>(`${this.apiUrl}/queue/leave/${user.id}`, {});
  }
}
