import { Component } from '@angular/core';

@Component({
  selector: 'standard-layout',
  templateUrl: './standard-layout.component.html',
  styleUrls: ['./standard-layout.component.scss'],
})
export class StandardLayoutComponent {
  isSidebarVisible: boolean = true;
  sideBarWidth: number = 250;

  constructor() {}

  toggleSidebar(): void {
    this.isSidebarVisible = !this.isSidebarVisible;
    if (!this.isSidebarVisible) {
      this.sideBarWidth = 0;
    } else {
      this.sideBarWidth = 250;
    }
  }
}
