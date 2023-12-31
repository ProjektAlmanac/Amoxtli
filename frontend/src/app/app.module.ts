import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'
import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { environment } from '../environments/environment'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatIconModule } from '@angular/material/icon'
import { MatToolbarModule } from '@angular/material/toolbar'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatButtonModule } from '@angular/material/button'

import { MatSidenavModule } from '@angular/material/sidenav'
import { MatMenuModule } from '@angular/material/menu'
import { NgIf } from '@angular/common'
import { MatBadgeModule } from '@angular/material/badge'
import { MatListModule } from '@angular/material/list'
import { MatCardModule } from '@angular/material/card'

import { ApiModule, Configuration, DefaultService, BASE_PATH } from 'src/generated/openapi'
import { HttpClientModule, HttpHeaders } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { SignUpModule } from './features/sign-up/sign-up.module'
import { ExchangeModule } from './features/exchange/exchange.module'
import { ServicioUsuario } from './core/services/servicio-usuario.service'
import { SharedModule } from './shared/shared.module'
import { SharedComponent } from './shared/shared.component'
import { CatalogoUsuarioModule } from './features/catalogo-usuario/catalogo-usuario.module'
import { NotFoundComponent } from './core/pages/not-found/not-found.component'
import { PerfilComponent } from './features/perfil/pages/perfil.component'
import { LoginModule } from './features/login/login.module'
import { LibroModule } from './features/libros/libro.module'

@NgModule({
  declarations: [AppComponent, NotFoundComponent, PerfilComponent, SharedComponent],
  imports: [
    NgIf,
    BrowserModule,
    AppRoutingModule,
    ApiModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatSidenavModule,
    MatMenuModule,
    MatBadgeModule,
    MatListModule,
    SignUpModule,
    ExchangeModule,
    SharedModule,
    MatCardModule,
    MatSnackBarModule,
    CatalogoUsuarioModule,
    LoginModule,
    LibroModule,
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.apiBasePath },
    ServicioUsuario,
    {
      provide: Configuration,
      useFactory: (userService: ServicioUsuario) =>
        new Configuration({
          accessToken: () => userService.token(),
        }),
      deps: [ServicioUsuario],
      multi: false,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(apiService: DefaultService) {
    apiService.defaultHeaders = new HttpHeaders({
      Prefer: 'dynamic=true',
    })
  }
}
