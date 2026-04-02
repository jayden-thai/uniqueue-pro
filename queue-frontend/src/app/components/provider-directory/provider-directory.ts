import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardModule } from '@angular/material/card';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInput, MatInputModule } from '@angular/material/input';
import { MatList, MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Observable } from 'rxjs';
import { User } from '../../services/auth.service';
import { QueueService, QueueSummary } from '../../services/queue.service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-provider-directory',
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule
  ],
  templateUrl: './provider-directory.html',
  styleUrl: './provider-directory.scss',
})
export class ProviderDirectory implements OnInit {
  private userService = inject(UserService);
  private queueService = inject(QueueService);
  
  // stream of providers for template to render, $ - observable stream, ! - forces compiler to assume its initialized
  providers$!: Observable<User[]>;

  // search input binding
  searchTerm = '';

  // tracks which provider is currently expanded (or none)
  expandedProviderId: number | null = null;

  // caches queues after first fetch
  queuesByProviderId: Record<number,QueueSummary[]> = {};

  // small UX helpers
  loadingQueuesFor: number | null = null;
  queueLoadErrorByProviderId: Record<number, string> = {};

  ngOnInit(): void {
    // initial load (all faculty)
    this.providers$ = this.userService.getFaculty();
  }

  onSearch() {
    // reload with the new search term
    this.providers$ = this.userService.getFaculty(this.searchTerm);
  }

  toggleQueues(provider: User) {
    if (!provider.id) return;

    // if clicking same provider, collapse without refetching
    if (this.expandedProviderId === provider.id) {
      this.expandedProviderId = null;
      return;
    }

    this.expandedProviderId = provider.id;

    // if already fetched queues for this provider, use cached data
    if (this.queuesByProviderId[provider.id]) {
      return;
    }

    // otherwise fetch from backend once
    this.loadingQueuesFor = provider.id;
    this.queueLoadErrorByProviderId[provider.id] = '';

    this.queueService.getQueuesForOwner(provider.id).subscribe({
      next: (queues) => {
        this.queuesByProviderId[provider.id!] = queues ?? [];
        this.loadingQueuesFor = null;
      },
      error: (err) => {
        this.loadingQueuesFor = null;
        this.queueLoadErrorByProviderId[provider.id!] =
          err?.error || err?.message || 'Failed to load queues';
      }
    });
  }

  getQueues(providerId: number): QueueSummary[] {
    return this.queuesByProviderId[providerId] ?? [];
  }
}
