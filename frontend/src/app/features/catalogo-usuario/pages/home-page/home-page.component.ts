import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { DefaultService, PaginaLibros, InfoBasicaLibro } from 'src/generated/openapi'

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.sass'],
})
export class HomePageComponent implements OnInit {
  public libros!: PaginaLibros
  public libroscopy!: InfoBasicaLibro[]
  public mostrarSpinner = false
  public isbnFilter = ''
  form: FormGroup

  public currentPage = 1
  public itemsPerPage = 10

  constructor(
    private serviceApi: DefaultService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      isbn: ['', [Validators.minLength(10), Validators.maxLength(13)]],
    })
  }

  ngOnInit(): void {
    this.mostrarSpinner = true
    this.recuperaLibros()
  }

  recuperaLibros() {
    this.currentPage = 1
    this.serviceApi.getLibros().subscribe(resp => {
      this.libros = resp
      this.libroscopy = resp.libros
      this.mostrarSpinner = false
    })
  }
  onSubmit() {
    this.mostrarSpinner = true
    if (!this.form.valid) {
      this.mostrarSpinner = false
      this.form.markAllAsTouched()
      return
    }

    const isbn = this.form.get('isbn')?.value
    if (isbn !== null && isbn !== undefined) {
      this.filtrarLibros(isbn) // Filtrar los libros basados en el ISBN
    } else {
      // En caso de que no se haya ingresado un ISBN, muestra el mensaje de "No se encontraron libros"
      this.libros.libros = this.libroscopy
      this.mostrarSpinner = false
    }
  }

  filtrarLibros(isbn: string) {
    if (isbn) {
      // Filter the libros array based on the entered ISBN
      this.mostrarSpinner = false
      this.libros.libros = this.libros.libros.filter(libro => libro.isbn.includes(isbn))
    } else {
      this.mostrarSpinner = false
      this.libros.libros = this.libroscopy
      this.form.reset()
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
    if (this.libroscopy) {
      return Math.ceil(this.libroscopy.length / this.itemsPerPage)
    } else {
      return 1 // Or handle this case as per your application's logic.
    }
  }
}
