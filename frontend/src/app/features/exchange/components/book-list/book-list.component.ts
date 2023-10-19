import { Component, EventEmitter, Input, Output } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { LibroRegistradoConDetalles, LibrosUsuario } from 'src/generated/openapi'
import { BookDialogComponent } from '../book-dialog/book-dialog.component'

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

  constructor(private dialog: MatDialog) {}

  showDetails(book: LibroRegistradoConDetalles) {
    this.dialog.open(BookDialogComponent, {
      data: { isbn: book.isbn },
    })
  }
}
