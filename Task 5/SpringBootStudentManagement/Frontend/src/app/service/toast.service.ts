import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'warning' | 'info';

export interface ToastItem {
  id: number;
  type: ToastType;
  title: string;
  message: string;
  removing?: boolean;
}

@Injectable({ providedIn: 'root' })
export class ToastService {
  toasts = signal<ToastItem[]>([]);
  private nextId = 0;

  private add(type: ToastType, title: string, message: string, duration = 3800) {
    const id = ++this.nextId;
    this.toasts.update(arr => [...arr, { id, type, title, message }]);
    setTimeout(() => this.startRemove(id), duration);
  }

  private startRemove(id: number) {
    this.toasts.update(arr => arr.map(t => t.id === id ? { ...t, removing: true } : t));
    setTimeout(() => this.toasts.update(arr => arr.filter(t => t.id !== id)), 380);
  }

  success(message: string, title = 'Success') { this.add('success', title, message); }
  error(message: string, title = 'Error')     { this.add('error', title, message, 5000); }
  warning(message: string, title = 'Warning') { this.add('warning', title, message); }
  info(message: string, title = 'Info')       { this.add('info', title, message); }
  remove(id: number)                          { this.startRemove(id); }
}
