import { Component, Inject, OnInit, signal } from '@angular/core'
import { DefaultService, DetallesLibro } from 'src/generated/openapi'
import { BookListDialogComponent } from '../book-list-dialog/book-list-dialog.component'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'

export interface DialogData {
  isbn: string
}

@Component({
  selector: 'app-book-dialog',
  templateUrl: './book-dialog.component.html',
  styleUrls: ['./book-dialog.component.sass'],
})
export class BookDialogComponent implements OnInit {
  bookDetails = signal<DetallesLibro | undefined>(undefined)

  constructor(
    private apiService: DefaultService,
    public dialogRef: MatDialogRef<BookListDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  ngOnInit() {
    this.apiService.getDetallesLibro(this.data.isbn).subscribe(this.bookDetails.set)
  }
}
