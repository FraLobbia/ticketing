import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ServizioProvaService {
  constructor() {}
  persone = [
    {
      name: 'Francesco',
      surname: 'Lobbia',
    },
    {
      name: 'Mario',
      surname: 'Rossi',
    },
    {
      name: 'Paolo',
      surname: 'Verdi',
    },
  ];

  getPersone() {
    return this.persone;
  }

  getPersona(index: number) {
    return this.persone[index];
  }
}
