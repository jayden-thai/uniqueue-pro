import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

export interface QueueItem {
  id?: number;
  name: string;
  universityId: string;
  email?: string;
  department?: string;
  role: 'STUDENT' | 'FACULTY';
  password?: string;
  arrivalTime?: string;
}

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;
  private currentUserName: string = '';

  // Getters
  getQueue(): Observable<QueueItem[]>{
    return this.http.get<QueueItem[]>(this.apiUrl);
  }

  getCurrentUser() {
    return this.currentUserName;
  }

  // Setters
  login(name: string) {
    this.currentUserName = name;
  }

  joinQueue() {
    if (!this.currentUserName) return; // Don't add if not logged in (redundant with current authentication guard implementation)

    const newPerson: QueueItem = {
      name: this.currentUserName,
      universityId: this.generateRandomId(),
      email: `${this.currentUserName}@university.edu`,
      password: 'defaultPassword123',
      role: 'STUDENT',
      department: 'Undeclared'
    };
    
    this.http.post<QueueItem>(this.apiUrl, newPerson).subscribe(() => {
      console.log('Sent to backend!');
    });
  }

  // ID generation
  private generateRandomId(): string {
    return Math.random().toString(36).substring(7);
  }
}
