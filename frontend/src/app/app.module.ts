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

import {
  ApiModule,
  Configuration,
  ConfigurationParameters,
  DefaultService,
  BASE_PATH,
} from 'src/generated/openapi'
import { HttpClientModule, HttpHeaders } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { PerfilComponent } from './features/perfil/pages/perfil.component'
import { SignUpModule } from './features/sign-up/sign-up.module'
import { ServicioUsuario } from './core/services/servicio-usuario.service'
import { SharedModule } from './shared/shared.module'
import { SharedComponent } from './shared/shared.component'
import { UserModule } from './features/user/user.module'

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {}
  return new Configuration(params)
}

@NgModule({
  declarations: [AppComponent, PerfilComponent, SharedComponent],
  imports: [
    NgIf,
    BrowserModule,
    AppRoutingModule,
    ApiModule.forRoot(apiConfigFactory),
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
    SharedModule,
    MatCardModule,
    MatSnackBarModule,
    UserModule,
  ],
  providers: [{ provide: BASE_PATH, useValue: environment.apiBasePath }, ServicioUsuario],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(apiService: DefaultService) {
    apiService.defaultHeaders = new HttpHeaders({
      Prefer: 'dynamic=true',
    })
  }
}
