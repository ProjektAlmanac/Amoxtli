import { Component, OnInit } from '@angular/core'
import { DefaultService, PaginaLibros, InfoBasicaLibro } from 'src/generated/openapi'

@Component({
  selector: 'app-mostrar-libros',
  templateUrl: './mostrar-libros.component.html',
  styleUrls: ['./mostrar-libros.component.sass'],
})
export class MostrarLibrosComponent implements OnInit {
  public libros!: PaginaLibros
  public libroscopy!: InfoBasicaLibro[]
  public mostrarSpinner = false
  public isbnFilter = ''

  public currentPage = 1
  public itemsPerPage = 10

  constructor(private serviceApi: DefaultService) {}

  ngOnInit(): void {
    this.mostrarSpinner = true
    this.recuperaLibros()
  }

  recuperaLibros() {
    this.currentPage = 1
    this.serviceApi.getLibros().subscribe(resp => {
      this.libros = resp
      this.libroscopy = resp.libros
      // eslint-disable-next-line no-console
      console.log(this.libros)
      this.mostrarSpinner = false
    })
  }

  filtrarLibros() {
    if (this.isbnFilter) {
      // Filter the libros array based on the entered ISBN
      this.libros.libros = this.libros.libros.filter(libro => libro.isbn.includes(this.isbnFilter))
    } else {
      this.libros.libros = this.libroscopy
    }
  }

  getBooksForCurrentPage(): InfoBasicaLibro[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage
    const endIndex = startIndex + this.itemsPerPage
    return this.libroscopy.slice(startIndex, endIndex)
  }

  goToPage(pageNumber: number) {
    if (pageNumber >= 1 && pageNumber <= this.getTotalPages()) {
      this.currentPage = pageNumber
    }
  }

  getTotalPages(): number {
    return Math.ceil(this.libroscopy.length / this.itemsPerPage)
  }
}
