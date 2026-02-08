import { Component, OnInit, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule} from '@angular/common';
import { QueueEntry, QueueService} from '../../services/queue.service';
import { Observable, timer, switchMap, shareReplay, map } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, RouterModule } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-queue',
  imports: [
    CommonModule,
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    RouterModule
  ],
  templateUrl: './queue.html',
  styleUrl: './queue.scss',
})
export class Queue implements OnInit {
  private route = inject(ActivatedRoute);
  private queueService = inject(QueueService);
  private authService = inject(AuthService);

  queueId!: number;
  currentUserId?: number;
  entries$!: Observable<QueueEntry[]>;
  isInQueue$!: Observable<boolean>;

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      throw new Error('Missing queue id');
    }
    this.queueId = Number(id);
    this.currentUserId = this.authService.getCurrentUser()?.id ?? undefined;

    this.entries$ = timer(0, 2000).pipe(
      switchMap(() => this.queueService.getQueueEntries(this.queueId)),
      shareReplay({ bufferSize: 1, refCount: true })
    );

    this.isInQueue$ = this.entries$.pipe(
      map(entries => {
        const uid = this.currentUserId;
        return !!uid && entries.some(e => e.user?.id === uid);
      })
    )
  }

  joinQueue() {
    try {
      this.queueService.joinCurrentUser(this.queueId).subscribe({
        next: () => console.log('Joined queue'),
        error: (err) => alert(err.error || err.message || 'Unable to join')
      });
    } catch (e: any) {
      alert('You must be logged in to join');
    }
  }

  leaveQueue() {
    try {
      this.queueService.leaveCurrentUser(this.queueId).subscribe({
        next: () => console.log('Left queue'),
        error: (err) => alert(err.error || err.message || 'Unable to leave')
      });
    } catch (e: any) {
      alert('You must be logged in to join.');
    }
  }

  isMe(entry: QueueEntry) {
    return !!this.currentUserId && entry.user?.id === this.currentUserId;
  }

}
