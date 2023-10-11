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
}
