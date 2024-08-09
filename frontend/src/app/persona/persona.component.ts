import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ServizioProvaService } from '../servizio-prova.service';

@Component({
  selector: 'app-persona',
  templateUrl: './persona.component.html',
  styleUrl: './persona.component.scss',
})
export class PersonaComponent implements OnInit {
  persona: any;
  id!: number;

  constructor(
    private route: ActivatedRoute,
    private servizioProva: ServizioProvaService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.id = +params.get('id')!;
      this.persona = this.servizioProva.getPersona(this.id);
    });
  }
}
