import { Injectable } from '@angular/core';

export interface QueueItem {
  name: string;
  joinedAt: Date;
}

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  // Global State: List of People
  private queue: QueueItem[] = [
    { name: 'Initial User', joinedAt: new Date()}
  ];

  // Global State: Current User's Name
  private currentUserName: string = '';

  // Getters
  getQueue() {
    return this.queue;
  }

  getCurrentUser() {
    return this.currentUserName;
  }

  // Setters
  login(name: string) {
    this.currentUserName = name;
  }

  joinQueue() {
    if (!this.currentUserName) return; // Don't add if not logged in

    const newItem: QueueItem = {
      name: this.currentUserName,
      joinedAt: new Date()
    };
    this.queue.push(newItem);
  }
}
