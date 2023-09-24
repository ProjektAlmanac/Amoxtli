import { HttpErrorResponse } from '@angular/common/http'
import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { DefaultService, Usuario, ModelError } from 'src/generated/openapi'

@Component({
  selector: 'app-sign-up-page',
  templateUrl: './sign-up-page.component.html',
  styleUrls: ['./sign-up-page.component.sass'],
})
export class SignInPageComponent {
  mensajeError = ''

  constructor(
    private servicioApi: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private router: Router
  ) {}

  onSubmit(usuario: Usuario) {
    this.servicioApi.crearUsuario(usuario).subscribe({
      next: value => {
        const id = value.id
        if (id) {
          this.servicioUsuario.setId(id)
          this.servicioApi.mandarCorreoConfirmacion(id)
          this.router.navigate(['/signup/verify'], {
            queryParams: {
              email: usuario.correo,
            },
          })
        }
      },
      error: (err: HttpErrorResponse) => {
        const error: ModelError = err.error
        this.mensajeError = error.mensaje
      },
    })
  }
}
