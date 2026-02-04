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
  }
  
}

export interface QueueSummary {
  id: number;
  title: string;
  createdAt: string;
  owner: {
    id: number;
    name: string;
  }
}

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private apiUrl = environment.apiUrl;

  getQueuesForOwner(ownerId: number): Observable<QueueSummary[]> {
    return this.http.get<QueueSummary[]>(`${this.apiUrl}/queues/owner/${ownerId}`);
  }

  getQueueEntries(queueId: number): Observable<QueueEntry[]>{
    return this.http.get<QueueEntry[]>(`${this.apiUrl}/queues/${queueId}/entries`);
  }

  createQueue(ownerId: number, title: string): Observable<QueueSummary> {
    return this.http.post<QueueSummary>(`${this.apiUrl}/queues?ownerId=${ownerId}&title=${encodeURIComponent(title)}`, {});
  }

  joinQueueAsUser(queueId: number, userId: number): Observable<QueueEntry> {
    return this.http.post<QueueEntry>(`${this.apiUrl}/queues/${queueId}/join/${userId}`, {});
  }

  leaveQueueAsUser(queueId: number, userId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/queues/${queueId}/leave/${userId}`, {});
  }

  joinCurrentUser(queueId: number): Observable<QueueEntry> {
    const user = this.auth.getCurrentUser();
    if (!user?.id) throw new Error('No logged-in user (missing id)'); 
    
    return this.joinQueueAsUser(queueId, user.id!);
  }

  leaveCurrentUser(queueId: number): Observable<void> {
    const user = this.auth.getCurrentUser();
    if (!user?.id) throw new Error('No logged-in user (missing id).');

    return this.leaveQueueAsUser(queueId, user.id!);
  }
}
