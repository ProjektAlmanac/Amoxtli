import { PerfilUsuario } from '../../../../../generated/openapi/model/perfilUsuario'
import { Component, OnInit } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import {
  CreacionIntercambio,
  DefaultService,
  DetallesLibro,
  Dueno,
  LibroConDuenos,
  ModelError,
  ValidaPuedeIntercambiar200Response,
} from 'src/generated/openapi'

@Component({
  selector: 'app-libro-page',
  templateUrl: './libro-page.component.html',
  styleUrls: ['./libro-page.component.sass'],
})
export class LibroPageComponent implements OnInit {
  public libroConDuenos!: LibroConDuenos
  public libro!: DetallesLibro
  public idUsuario!: number
  public usuario!: PerfilUsuario
  public isbn!: string
  public desactivarBotonIntercambiar = true
  public mostrarNotificacionSucess = false
  public mostrarNotificacionInfo = false
  public mostrarNotificacionError = false
  public mensajeInfo!: string
  public mensajeError!: string
  public validado = false
  public spinnerProceso = true

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private route: ActivatedRoute
  ) {
    this.idUsuario = this.servicioUsuario.id()
    this.servicioAPI.getUsuario(this.idUsuario).subscribe(data => {
      this.usuario = data
      this.usuario.fotoPerfil ??=
        'https://investigacion.unimagdalena.edu.co/Content/Imagenes/userVacio.png'
    })
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.isbn = params['isbn']
      this.servicioAPI.getLibro(this.isbn).subscribe(data => {
        this.libroConDuenos = data
        // En caso de no tener foto, se pone una en automatico
        this.libroConDuenos.duenos.forEach(dueno => {
          dueno.foto ??= 'https://investigacion.unimagdalena.edu.co/Content/Imagenes/userVacio.png'
        })
        this.libro = { ...this.libroConDuenos }
        this.spinnerProceso = false
      })
    })

    this.servicioAPI.validaPuedeIntercambiar(this.idUsuario).subscribe({
      next: (data: ValidaPuedeIntercambiar200Response) => {
        this.desactivarBotonIntercambiar = !data.puedeIntercambiar ?? true
        if (this.desactivarBotonIntercambiar) {
          this.mensajeInfo = data.mensaje ?? 'No puedes intercambiar'
          this.mostrarNotificacionInfo = true
        }
      },
      error: (error: ModelError) => {
        this.mensajeError = error.mensaje
        this.mostrarNotificacionError = true
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }

  intercambiar(aceptante: Dueno) {
    this.desactivarBotonIntercambiar = true
    const intercambio: CreacionIntercambio = {
      idAceptante: aceptante.id,
      idLibroAceptante: parseInt(this.isbn),
    }
    this.servicioAPI.addIntercambio(this.idUsuario, intercambio).subscribe({
      next: () => {
        this.mostrarNotificacionSucess = true
      },
      error: (error: ModelError) => {
        this.mensajeError = error.mensaje
        this.mostrarNotificacionError = true
        this.desactivarBotonIntercambiar = false
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }
}
