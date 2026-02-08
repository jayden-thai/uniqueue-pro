import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { QueueService, QueueSummary } from '../../services/queue.service';
import { AuthService, User } from '../../services/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-faculty-dashboard',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatButtonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './faculty-dashboard.html',
  styleUrl: './faculty-dashboard.scss',
  standalone: true
})
export class FacultyDashboard {
  queues$!: Observable<QueueSummary[]>;
  user!: User;
  userId!: number;
  userName!: string;

  greeting!: string;
  newQueueTitle: string = '';
  showQueueCreateForm!: boolean;


  authService = inject(AuthService);
  queueService = inject(QueueService);

  ngOnInit() {
    this.user = this.authService.getCurrentUser()!;
    this.userId = this.user.id!;
    this.userName = this.user.name!;
    this.greeting = this.getGreeting();
    this.showQueueCreateForm = false;

    this.loadQueues();
  }

  private getGreeting(): string {
    const hour = new Date().getHours();

    if (hour < 12) {
      return 'Good morning';
    } else if (hour < 18) {
      return 'Good afternoon';
    } else {
      return 'Good evening';
    }
  }

  loadQueues() {
    this.queues$ = this.queueService.getQueuesForOwner(this.userId);
  }

  createQueue() {
    const title = this.newQueueTitle.trim();
    if (!title) return;

    this.queueService.createQueue(this.userId, this.newQueueTitle).subscribe({
      next: () => {
        this.newQueueTitle = '';
        this.showQueueCreateForm = false;
        this.loadQueues();
      },
      error: (err) => alert(err.error || err.message || 'Unable to create queue')
    });
  }
}
