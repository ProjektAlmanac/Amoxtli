import { PerfilUsuario } from './../../../../../generated/openapi/model/perfilUsuario'
import { Component, Input, inject } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
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
export class IntercambioPageComponent {
  @Input() // Es el libro con los dueños
  public libroConDuenos: LibroConDuenos = {
    isbn: '1234567896',
    autor: 'Andres Lopez',
    titulo: 'El libro de Andres',
    urlPortada: 'https://edit.org/images/cat/portadas-libros-big-2019101610.jpg',
    generos: ['miedo', 'terror', 'suspenso'],
    editorial: 'Grijalbo',
    sinopsis: 'Ninguna sinopsis',
    idioma: 'Spanish',
    fechaPublicacion: '1998-03-11',
    duenos: [
      {
        id: 1,
        nombre: 'Miguel',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 2,
        nombre: 'Axel',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 3,
        nombre: 'Julian',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 4,
        nombre: 'OScar',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 5,
        nombre: 'Paola',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 6,
        nombre: 'Mari Carmen',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 7,
        nombre: 'Valentin',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
      {
        id: 8,
        nombre: 'Joaquin',
        apellido: 'Guzman',
        foto: 'https://us.123rf.com/450wm/yupiramos/yupiramos1709/yupiramos170930757/87002882-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg',
      },
    ],
  }

  public libro: DetallesLibro = {
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

  // Si es true, está deshabilitado
  public desactivarBotonIntercambiar = true

  public readonly idUsuario!: number
  public usuario!: PerfilUsuario

  private snackBar = inject(MatSnackBar)

  constructor(
    private servicioAPI: DefaultService,
    private servicioUsuario: ServicioUsuario
  ) {
    this.idUsuario = this.servicioUsuario.id.value
    this.servicioAPI.getUsuario(this.idUsuario).subscribe(data => (this.usuario = data))
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
