import { IMenuItem } from './../interfaces/menu-item.interface';

export const MENU_ITEMS: IMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'bi-card-list',
    path: '/tickets',
    isActive: false,
  },
  {
    title: 'Ticket di oggi',
    icon: 'bi-calendar-event',
    path: 'today',
    isActive: false,
  },
  {
    title: 'In pending',
    icon: 'bi-hourglass-split',
    path: 'pending',
    isActive: false,
  },
  {
    title: 'Impostazioni',
    icon: 'bi-gear-wide-connected',
    path: 'settings',
    isActive: false,
  },
  // Aggiungi qui altre voci di menu
];
