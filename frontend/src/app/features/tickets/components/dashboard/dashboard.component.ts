import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  tickets = [
    {
      id: 1,
      title: 'Problema con il server',
      description: 'Il server non risponde alle richieste HTTP',
      status: 'In lavorazione',
    },
    {
      id: 2,
      title: 'Errore di autenticazione',
      description: "Impossibile accedere all'applicazione",
      status: 'Chiuso',
    },
    {
      id: 3,
      title: 'Problema con il database',
      description: 'Impossibile connettersi al database',
      status: 'In lavorazione',
    },
  ];
}
