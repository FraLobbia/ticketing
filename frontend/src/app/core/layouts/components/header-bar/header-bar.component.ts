import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { IJwtPayload } from '../../../../shared/interfaces/auth.interface';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrl: './header-bar.component.scss',
})
export class HeaderBarComponent implements OnInit {

  @Output() toggleSidebar = new EventEmitter<void>();
  nomeCompleto: String = '';
  isAdministrator = false;

  constructor(protected authService: AuthService, protected router: Router, private location: Location) { }

  ngOnInit(): void {
    const jwtPayload: IJwtPayload | null = this.authService.getTokenPayload();
    if (jwtPayload) {
      this.nomeCompleto = `${jwtPayload.name} ${jwtPayload.surname}`;
      this.isAdministrator = this.authService.isAdminUser();
    }
  }
  goBack() {
    this.location.back();
  }
  logout(): void {
    this.authService.logout();
  }

  onToggleSidebar(): void {
    this.toggleSidebar.emit();
  }
}
