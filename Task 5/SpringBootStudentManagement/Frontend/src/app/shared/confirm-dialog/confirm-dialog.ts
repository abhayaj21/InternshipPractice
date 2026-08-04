import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { ConfirmService } from '../../service/confirm.service';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [NgIf],
  templateUrl: './confirm-dialog.html',
  styleUrl: './confirm-dialog.css'
})
export class ConfirmDialog {
  confirm = inject(ConfirmService);
}
