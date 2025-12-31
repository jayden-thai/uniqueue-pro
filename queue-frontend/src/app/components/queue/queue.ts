import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, DatePipe } from '@angular/common';
import { QueueService, QueueItem } from '../../services/queue.service';
import { Observable, timer, switchMap } from 'rxjs';

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
    this.queueList$ = timer(0, 2000).pipe(
      switchMap(() => this.queueService.getQueue())
    );
  }

  joinQueue() {
    this.queueService.joinQueue();
  }

}
