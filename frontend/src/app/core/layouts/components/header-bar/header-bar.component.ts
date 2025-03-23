import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { IJwtPayload } from '../../../../shared/interfaces/auth.interface';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrl: './header-bar.component.scss',
})
export class HeaderBarComponent implements OnInit {
  @Output() toggleSidebar = new EventEmitter<void>();
  nomeCompleto: String = '';
  isAdministrator = false;

  constructor(protected authService: AuthService) { }

  ngOnInit(): void {
    const jwtPayload: IJwtPayload | null = this.authService.getTokenPayload();
    if (jwtPayload) {
      this.nomeCompleto = `${jwtPayload.name} ${jwtPayload.surname}`;
      this.isAdministrator = this.authService.isAdminUser();
    }
  }

  logout(): void {
    this.authService.logout();
  }

  onToggleSidebar(): void {
    this.toggleSidebar.emit();
  }
}
