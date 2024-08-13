import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface MenuItem {
  path: string;
  title: string;
  icon: string;
  isActive: boolean;
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  menuItems = [
    {
      title: 'Dashboard',
      icon: 'dashboard',
      path: '/tickets',
      isActive: false,
    },
    {
      title: 'Apri Nuovo Ticket',
      icon: 'add_circle',
      path: '/tickets/create',
      isActive: false,
    },
    // Aggiungi qui altre voci di menu per moduli protetti
  ];

  constructor(private router: Router) {}

  toggleActive(selectedItem: MenuItem): void {
    // Reimposta lo stato attivo su tutti gli elementi del menu
    this.menuItems.forEach((item) => (item.isActive = false));

    // Imposta l'elemento cliccato come attivo
    selectedItem.isActive = true;

    // Naviga alla rotta selezionata
    this.router.navigate([selectedItem.path]);
  }
}
