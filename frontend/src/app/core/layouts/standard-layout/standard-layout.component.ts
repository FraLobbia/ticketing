import { Component } from '@angular/core';

@Component({
  selector: 'standard-layout',
  templateUrl: './standard-layout.component.html',
  styleUrls: ['./standard-layout.component.scss'],
})
export class StandardLayoutComponent {
  /**
   * Campi
   */
  isSidebarVisible: boolean = true;
  sideBarWidth: number = 250; //px

  /**
   * Costruttore
   */
  constructor() {}

  /**
   * Mostra o nasconde la sidebar
   */
  toggleSidebar(): void {
    this.isSidebarVisible = !this.isSidebarVisible;
    if (!this.isSidebarVisible) {
      this.sideBarWidth = 0;
    } else {
      this.sideBarWidth = 250;
    }
  }
}
