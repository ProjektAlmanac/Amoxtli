import { PerfilUsuario } from './../../../../../generated/openapi/model/perfilUsuario'
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
  selector: 'app-intercambio-page',
  templateUrl: './intercambio-page.component.html',
  styleUrls: ['./intercambio-page.component.sass'],
})
export class IntercambioPageComponent implements OnInit {
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
  public progessSpinner = true

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private route: ActivatedRoute
  ) {
    this.servicioUsuario.id.subscribe(id => {
      this.idUsuario = id
      this.servicioAPI.getUsuario(this.idUsuario).subscribe(data => {
        this.usuario = data
        this.usuario.fotoPerfil =
          this.usuario.fotoPerfil ??
          'https://investigacion.unimagdalena.edu.co/Content/Imagenes/userVacio.png'
      })
    })
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.isbn = params['isbn']
      this.isbn = '1234567896'
      this.servicioAPI.getLibro(this.isbn).subscribe(data => {
        this.libroConDuenos = data
        // En caso de no tener foto, se pone una en automatico
        this.libroConDuenos.duenos.forEach(dueno => {
          dueno.foto =
            dueno.foto ?? 'https://investigacion.unimagdalena.edu.co/Content/Imagenes/userVacio.png'
        })
        this.libro = {
          isbn: this.libroConDuenos.isbn,
          autor: this.libroConDuenos.autor,
          titulo: this.libroConDuenos.titulo,
          urlPortada: this.libroConDuenos.urlPortada,
          generos: this.libroConDuenos.generos,
          editorial: this.libroConDuenos.editorial,
          sinopsis: this.libroConDuenos.sinopsis,
          idioma: this.libroConDuenos.idioma,
          fechaPublicacion: this.libroConDuenos.fechaPublicacion,
        }
        this.progessSpinner = false
      })
    })

    // Validar si el usuario puede intercambiar
    this.servicioAPI.getLibrosUsuario(this.idUsuario).subscribe({
      next: data => {
        // Si no tiene libros, no puede intercambiar
        if (data.libros.length === 0) {
          this.desactivarBotonIntercambiar = true
          this.mensajeInfo = 'No tienes libros para intercambiar.'
          this.mostrarNotificacionInfo = true
        } else {
          // Si tiene libros, valida que no tenga 4 intercambios activos
          this.servicioAPI.validaPuedeIntercambiar(this.idUsuario).subscribe({
            next: (data: ValidaPuedeIntercambiar200Response) => {
              this.desactivarBotonIntercambiar = !data.puedeIntercambiar ?? true
              if (this.desactivarBotonIntercambiar) {
                this.mensajeInfo = 'Tienes 4 intercambios activos.'
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
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }
}
