import { HttpErrorResponse } from '@angular/common/http'
import { Component } from '@angular/core'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { Credenciales, DefaultService, ModelError } from 'src/generated/openapi'

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.sass'],
})
export class LoginPageComponent {
  public mensajeError = ''

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario
  ) {}

  onSubmit(credenciales: Credenciales) {
    this.servicioAPI.iniciarSesion(credenciales).subscribe({
      error: (err: HttpErrorResponse) => {
        const error: ModelError = err.error
        this.mensajeError = error.mensaje
      },
    })
  }
}
