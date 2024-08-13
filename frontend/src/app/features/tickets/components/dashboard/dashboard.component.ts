import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  constructor(private authService: AuthService) {}

  private id!: string | null;

  ngOnInit(): void {
    // Assicurati di utilizzare authService solo dopo che è stato inizializzato
    const id = this.getId();
    console.log('User ID:', id);
  }

  getId(): string | null {
    const token = this.authService.getToken();
    console.log('Token:', token);
    this.id = this.authService.getUserIdFromToken(token!);
    return this.id;
  }

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
