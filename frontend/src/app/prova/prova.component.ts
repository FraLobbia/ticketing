import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-prova',
  templateUrl: './prova.component.html',
  styleUrl: './prova.component.scss',
})
export class ProvaComponent implements OnInit, OnChanges {
  @Input() data: any;
  @Output() mandaDatiEvento = new EventEmitter<string[]>();

  mandaDati() {
    this.mandaDatiEvento.emit(this.gatti);
  }

  gatti = ['siamese', 'europeo', 'sphinx'];

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes);
  }

  isDisabled = true;

  constructor() {}
  ngOnInit(): void {
    // setInterval(() => {
    //   console.log('isDisabled', this.isDisabled);
    //   this.isDisabled = !this.isDisabled;
    // }, 2000);
  }
}
