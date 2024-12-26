import { IMenuItem } from './../interfaces/menu-item.interface';

export const MENU_ITEMS: IMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'dashboard',
    path: '/tickets',
    isActive: false,
  },
  {
    title: 'Ticket di oggi',
    icon: 'calendar_month',
    path: 'today',
    isActive: false,
  },
  {
    title: 'In pending',
    icon: 'pending_actions',
    path: 'pending',
    isActive: false,
  },
  {
    title: 'Impostazioni',
    icon: 'settings',
    path: 'settings',
    isActive: false,
  },
  // Aggiungi qui altre voci di menu
];
