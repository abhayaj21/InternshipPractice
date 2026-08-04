import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { Sidebar } from './sidebar/sidebar';
import { RouterOutlet } from '@angular/router';
import { SidebarStateService } from '../service/sidebar-state.service';

@Component({
  selector: 'app-dashboard',
  imports: [Sidebar, RouterOutlet, NgIf],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {
  sidebarState = inject(SidebarStateService);
}
