import { Component, OnInit } from '@angular/core';
import { ServizioProvaService } from '../servizio-prova.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss',
})
export class ContactComponent implements OnInit {
  constructor(private servizioProva: ServizioProvaService) {}

  persone: any;
  isProfile!: boolean;

  ngOnInit(): void {
    this.persone = this.servizioProva.getPersone();
  }
}
