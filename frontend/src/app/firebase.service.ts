import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FirebaseService {
  constructor(private http: HttpClient) {}

  url =
    'https://corso-angular-7bfb8-default-rtdb.europe-west1.firebasedatabase.app/';

  insertPersona(body: {}) {
    return this.http.post(this.url + 'persona.json', body);
  }

  getPersone() {
    return this.http.get(this.url + 'persona.json');
  }
}
