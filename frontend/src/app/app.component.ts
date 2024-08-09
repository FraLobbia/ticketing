import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit, AfterViewInit {
  ngAfterViewInit(): void {
    console.log(this.valoreInput);
  }
  title = 'frontend';

  colore = '';

  @ViewChild('inputSaluti') valoreInput!: ElementRef;

  persone = [
    { nome: 'Mario', cognome: 'Rossi' },
    { nome: 'Luca', cognome: 'Verdi' },
    { nome: 'Paolo', cognome: 'Bianchi' },
  ];

  ngOnInit(): void {
    console.log(this.valoreInput);
  }

  cambiaPersone(): any {
    console.log('cambia persone');
    this.persone = [
      { nome: 'rsgregw', cognome: 'Rossi' },
      { nome: 'Lueragwca', cognome: 'Verdi' },
      { nome: 'Paoregqtaglo', cognome: 'Bianchi' },
    ];
  }

  onClick(e: Event): void {
    this.title = (<HTMLInputElement>e.target).value;
  }

  onRiceviDati(value: string[]) {
    console.log(value[0]);
  }

  onInput(event: Event): void {
    console.log((<HTMLInputElement>event.target).value);
  }

  cambiaColoreEvid(colore: string) {
    this.colore = colore;
  }
}
