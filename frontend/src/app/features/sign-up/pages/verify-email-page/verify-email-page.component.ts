import { HttpErrorResponse } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { ActivatedRoute, Router } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { DefaultService, ModelError } from 'src/generated/openapi'

@Component({
  selector: 'app-verify-email-page',
  templateUrl: './verify-email-page.component.html',
  styleUrls: ['./verify-email-page.component.sass'],
})
export class VerifyEmailPageComponent implements OnInit {
  mensajeError = ''

  email = ''

  constructor(
    private servicioApi: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private router: Router,
    private route: ActivatedRoute,
    private snackbar: MatSnackBar
  ) {}

  onSubmit({ code }: { code: string }) {
    this.servicioApi.verificarCorreo(this.servicioUsuario.id(), { codigo: code }).subscribe({
      next: () => {
        this.router.navigate(['/welcome'])
      },
      error: (err: HttpErrorResponse) => {
        const error: ModelError = err.error
        this.mensajeError = error.mensaje
      },
    })
  }

  resendCode() {
    this.servicioApi.mandarCorreoConfirmacion(this.servicioUsuario.id()).subscribe({
      next: () => {
        this.snackbar.open('Correo enviado', 'Aceptar')
      },
      error: this.mostrarError.bind(this),
    })
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.email = params['email']
    })
    this.servicioApi.mandarCorreoConfirmacion(this.servicioUsuario.id()).subscribe({
      error: this.mostrarError.bind(this),
    })
  }

  mostrarError(err: HttpErrorResponse) {
    const error: ModelError = err.error
    this.snackbar.open(error.mensaje, 'Aceptar')
  }
}
