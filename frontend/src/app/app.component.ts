import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { interval, Observable } from 'rxjs';
import { FirebaseService } from './firebase.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  constructor(private firebase: FirebaseService) {}
  // @ViewChild('homeform') homeform!: NgForm;
  homeform!: FormGroup;

  onSubmit() {
    console.log('submit');
    this.firebase
      .insertPersona({
        name: this.homeform.value.nome,
        email: this.homeform.value.email,
      })
      .subscribe((data) => {
        console.log(data);
      });
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
    this.homeform = new FormGroup({
      nome: new FormControl(null, Validators.required),
      email: new FormControl(null, [Validators.required, Validators.email]),
    });

    this.firebase.getPersone().subscribe((data: any) => {
      console.log(data);
      this.persone = Object.keys(data).map((key) => {
        return data[key];
      });
      console.log(this.persone);
    });
    // new Observable((observer) => {
    //   let count = 0;
    //   setInterval(() => {
    //     observer.next(count);
    //     count++;
    //   }, 1000);
    // }).subscribe((numero) => {
    //   console.log(numero);
    // });
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
