import { Component, Inject, OnInit, signal } from '@angular/core'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { DefaultService, LibroRegistradoConDetalles, LibrosUsuario } from 'src/generated/openapi'

interface DialogData {
  userId: number
}

@Component({
  selector: 'app-book-list-dialog',
  templateUrl: './book-list-dialog.component.html',
  styleUrls: ['./book-list-dialog.component.sass'],
})
export class BookListDialogComponent implements OnInit {
  userBooks = signal<LibrosUsuario | undefined>(undefined)

  constructor(
    private apiService: DefaultService,
    public dialogRef: MatDialogRef<BookListDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  ngOnInit() {
    this.apiService.getLibrosUsuario(this.data.userId).subscribe(this.userBooks.set)
  }

  selectBook(book: LibroRegistradoConDetalles) {
    this.dialogRef.close(book)
  }
}
