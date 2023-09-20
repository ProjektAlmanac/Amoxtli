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

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {}
  return new Configuration(params)
}

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, ApiModule.forRoot(apiConfigFactory), HttpClientModule],
  providers: [{ provide: BASE_PATH, useValue: environment.apiBasePath }],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(apiService: DefaultService) {
    apiService.defaultHeaders = new HttpHeaders({
      Prefer: 'dynamic=true',
    })
  }
}
