import { HttpErrorResponse } from '@angular/common/http'
import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { Credenciales, DefaultService, SessionToken, ModelError } from 'src/generated/openapi'

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.sass'],
})
export class LoginPageComponent {
  public mensajeError = ''

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private router: Router
  ) {}

  onSubmit(credenciales: Credenciales) {
    this.servicioAPI.iniciarSesion(credenciales).subscribe({
      next: (sessionToken: SessionToken) => {
        this.servicioUsuario.id.set(sessionToken.idUsuario)
        this.servicioUsuario.token.set(sessionToken.token)
        this.router.navigate(['/home'])
      },
      error: (err: HttpErrorResponse) => {
        const error: ModelError = err.error
        this.mensajeError = error.mensaje
      },
    })
  }
}
