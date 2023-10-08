import { Component, EventEmitter, Input, Output } from '@angular/core'
import { LibroRegistradoConDetalles, LibrosUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.sass'],
})
export class BookListComponent {
  @Input({ required: true })
  userBooks?: LibrosUsuario

  @Output()
  selected = new EventEmitter<LibroRegistradoConDetalles>()
}
