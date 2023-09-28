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

  idUsuario = 0

  constructor(
    private servicioApi: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private router: Router,
    private route: ActivatedRoute,
    private snackbar: MatSnackBar
  ) {}

  onSubmit({ code }: { code: string }) {
    this.servicioApi.verificarCorreo(this.idUsuario, { codigo: code }).subscribe({
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
    this.servicioApi.mandarCorreoConfirmacion(this.idUsuario).subscribe({
      next: () => {
        this.snackbar.open('Correo enviado', 'Aceptar')
      },
      error: (err: HttpErrorResponse) => {
        const error: ModelError = err.error
        this.snackbar.open(error.mensaje, 'Aceptar')
      },
    })
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.email = params['email']
    })
    this.servicioUsuario.id.subscribe(id => {
      this.idUsuario = id
    })
  }
}
