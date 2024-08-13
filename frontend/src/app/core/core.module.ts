import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginModule } from './login-register-components/login/login.module';
import { RegisterModule } from './login-register-components/register/register.module';
import { LayoutsModule } from './layouts/layouts.module';

const modules = [LoginModule, RegisterModule, LayoutsModule];

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ...modules],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  declarations: [],
  exports: [...modules],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it in the AppModule only.'
      );
    }
  }
}
