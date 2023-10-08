import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'

import { environment } from '../environments/environment'

import {
  ApiModule,
  Configuration,
  ConfigurationParameters,
  DefaultService,
  BASE_PATH,
} from 'src/generated/openapi'
import { HttpClientModule, HttpHeaders } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { SignUpModule } from './features/sign-up/sign-up.module'
import { ExchangeModule } from './features/exchange/exchange.module'
import { ServicioUsuario } from './core/services/servicio-usuario.service'
import { SharedModule } from './shared/shared.module'
import { CatalogoUsuarioModule } from './features/catalogo-usuario/catalogo-usuario.module'
import { NotFoundComponent } from './core/pages/not-found/not-found.component'

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {}
  return new Configuration(params)
}

@NgModule({
  declarations: [AppComponent, NotFoundComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ApiModule.forRoot(apiConfigFactory),
    HttpClientModule,
    BrowserAnimationsModule,
    SignUpModule,
    ExchangeModule,
    SharedModule,
    CatalogoUsuarioModule,
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
