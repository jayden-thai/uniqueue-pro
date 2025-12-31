import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, DatePipe } from '@angular/common';
import { QueueService, QueueItem } from '../../services/queue.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-queue',
  imports: [
    CommonModule,
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    DatePipe
  ],
  templateUrl: './queue.html',
  styleUrl: './queue.scss',
})
export class Queue implements OnInit {

  queueList$!: Observable<QueueItem[]>;

  constructor(public queueService: QueueService) {}

  ngOnInit() {
    this.queueList$ = this.queueService.getQueue();
  }

  joinQueue() {
    this.queueService.joinQueue();

    // Refreshes list after short delay
    setTimeout(() => {
      this.queueList$ = this.queueService.getQueue();
    }, 200);
  }

}
