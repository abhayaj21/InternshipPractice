import { Component, inject } from '@angular/core';
import { Router, RouterLinkActive, RouterLinkWithHref } from '@angular/router';
import { SidebarStateService } from '../../service/sidebar-state.service';
import { ToastService } from '../../service/toast.service';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLinkActive, RouterLinkWithHref],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class Sidebar {
  private route = inject(Router);
  sidebarState = inject(SidebarStateService);
  private toast = inject(ToastService);
  isStudent = false;

  checkUrl() {
    const url = this.route.url;
    this.isStudent = url.includes('/student');
    this.sidebarState.close();
  }

  onLogout() {
    this.toast.info('You have been logged out successfully.', 'Logged Out');
    setTimeout(() => {
      sessionStorage.clear();
      window.history.pushState({}, '', '/');
      window.location.reload();
    }, 800);
  }
}
