import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor() {}

  showSuccess(message: string): void {
    this.show(message, 'success');
  }

  showError(message: string): void {
    this.show(message, 'error');
  }

  private show(message: string, type: string): void {
    const body = document.body;
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    body.appendChild(notification);

    setTimeout(() => {
      body.removeChild(notification);
    }, 5000); // Customize duration here
  }
}
