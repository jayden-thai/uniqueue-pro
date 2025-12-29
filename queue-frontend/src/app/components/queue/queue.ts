import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { QueueService, QueueItem } from '../../services/queue.service';

@Component({
  selector: 'app-queue',
  imports: [
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    DatePipe
  ],
  templateUrl: './queue.html',
  styleUrl: './queue.scss',
})
export class Queue {

  constructor(public queueService: QueueService) {}

  get queueList(): QueueItem[] {
    return this.queueService.getQueue();
  }

  joinQueue() {
    this.queueService.joinQueue();
  }

}
