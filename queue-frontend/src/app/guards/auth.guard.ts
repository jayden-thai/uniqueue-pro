import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { QueueService } from "../services/queue.service";

export const authGuard = () => {
    const queueService = inject(QueueService);
    const router = inject(Router);

    if (queueService.getCurrentUser()) {
        return true;
    } else {
        router.navigate(['/login']);
        return false;
    }
}