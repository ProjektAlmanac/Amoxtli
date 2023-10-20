import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { DefaultService, PaginaLibros } from 'src/generated/openapi'

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.sass'],
})
export class HomePageComponent implements OnInit {
  public paginaLibros: PaginaLibros | undefined
  public mostrarSpinner = false
  public currentPage = 1
  form: FormGroup

  constructor(
    private serviceApi: DefaultService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      isbn: ['', [Validators.minLength(10), Validators.maxLength(13)]],
    })
  }

  ngOnInit(): void {
    this.mostrarSpinner = true
    this.recuperaLibros(this.currentPage)
  }

  recuperaLibros(page: number) {
    this.serviceApi.getLibros(page).subscribe(paginaLibros => {
      this.paginaLibros = paginaLibros
      this.currentPage = page
      this.mostrarSpinner = false
    })
  }

  onSubmit() {
    if (!this.form.valid) {
      this.form.markAllAsTouched()
      return
    }

    const isbn = this.form.get('isbn')?.value
    if (isbn === null || isbn === undefined) return

    this.mostrarSpinner = true

    this.serviceApi.getDetallesLibro(isbn).subscribe({
      next: () => {
        this.router.navigate(['/libro'], { queryParams: { isbn } })
      },
      error: () => {
        // Mostrar el mismo mensaje de error que cuando no se encuentran libros
        this.paginaLibros = undefined
      },
    })
  }

  irAnterior() {
    if (this.paginaLibros === undefined) return
    this.recuperaLibros(this.paginaLibros.pagAnterior)
  }

  irSiguiente() {
    if (this.paginaLibros === undefined) return
    this.recuperaLibros(this.paginaLibros.pagSiguiente)
  }
}
