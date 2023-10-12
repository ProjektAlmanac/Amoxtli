import { PerfilUsuario } from './../../../../../generated/openapi/model/perfilUsuario'
import { Component, OnInit, inject } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { ActivatedRoute } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { successNotification } from 'src/app/shared/config/LibreryConfig'
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
  public desactivarBotonIntercambiar = true
  public idUsuario!: number
  public usuario!: PerfilUsuario
  private snackBar = inject(MatSnackBar)
  public isbn!: string

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.isbn = params['isbn']
      this.isbn = '1234567896'
      this.servicioAPI.getLibro(this.isbn).subscribe(data => {
        this.libroConDuenos = data
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
      })
    })
    this.servicioUsuario.id.subscribe(id => {
      this.idUsuario = id
      this.servicioAPI.getUsuario(this.idUsuario).subscribe(data => (this.usuario = data))
    })
  }

  validaPuedeIntercambiar() {
    this.desactivarBotonIntercambiar = true
    this.servicioAPI.validaPuedeIntercambiar(this.idUsuario).subscribe({
      next: (data: ValidaPuedeIntercambiar200Response) => {
        this.desactivarBotonIntercambiar = !data.puedeIntercambiar ?? true
        if (this.desactivarBotonIntercambiar) {
          successNotification('Actualmente tienes 4 solicitudes de intercambio', this.snackBar)
        }
      },
      error: (error: ModelError) => {
        successNotification(error.mensaje, this.snackBar)
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }

  intercambiar(aceptante: Dueno) {
    const intercambio: CreacionIntercambio = {
      idAceptante: aceptante.id,
      idLibroAceptante: parseInt(this.libro.isbn),
    }
    this.servicioAPI.addIntercambio(this.idUsuario, intercambio).subscribe({
      next: () => {
        successNotification('Solicitud de intercambio creada', this.snackBar)
      },
      error: (error: ModelError) => {
        successNotification(error.mensaje, this.snackBar)
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }

  success(msg: string) {
    successNotification(msg, this.snackBar)
  }
}
