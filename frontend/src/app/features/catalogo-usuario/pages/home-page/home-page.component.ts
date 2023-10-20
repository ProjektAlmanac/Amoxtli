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
  public ant = 0
  public sig = 0
  public res = 0

  public currentPage = 1

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
    this.serviceApi.getLibros(this.currentPage).subscribe(resp => {
      this.libros = resp
      this.libroscopy = resp.libros
      this.ant = resp.pagAnterior
      this.sig = resp.pagSiguiente
      this.res = resp.resultados
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

  irAnterior() {
    this.currentPage = this.ant
    this.recuperaLibros()
  }

  irSiguiente() {
    this.currentPage = this.sig
    this.recuperaLibros()
  }
}
