import { Component, Input } from '@angular/core'
import { DetallesLibro, LibroConDuenos } from 'src/generated/openapi'

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
    urlPortada: 'string',
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
        foto: null,
      },
      {
        id: 2,
        nombre: 'Axel',
        apellido: 'Guzman',
        foto: null,
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
}
