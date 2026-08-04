import { Injectable, signal } from '@angular/core';

interface ConfirmState {
  visible: boolean;
  message: string;
  subtitle: string;
  resolve?: (v: boolean) => void;
}

@Injectable({ providedIn: 'root' })
export class ConfirmService {
  state = signal<ConfirmState>({ visible: false, message: '', subtitle: '' });

  confirm(message: string, subtitle = 'This action cannot be undone.'): Promise<boolean> {
    return new Promise(resolve => {
      this.state.set({ visible: true, message, subtitle, resolve });
    });
  }

  respond(result: boolean) {
    const resolve = this.state().resolve;
    this.state.set({ visible: false, message: '', subtitle: '' });
    resolve?.(result);
  }
}
