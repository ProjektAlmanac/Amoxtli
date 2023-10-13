import { Component, OnInit } from '@angular/core'
import { DefaultService, LibroConDuenos } from 'src/generated/openapi'
import { ActivatedRoute } from '@angular/router'

@Component({
  selector: 'app-detalles-libro',
  templateUrl: './detalles-libro.component.html',
  styleUrls: ['./detalles-libro.component.sass'],
})
export class DetallesLibroComponent implements OnInit {
  public Libro!: LibroConDuenos
  public mostrarSpinner = false
  public mostrarLibro = false
  public mostrarNotificacionWarn = false
  public isbn: string
  constructor(
    private serviceApi: DefaultService,
    private route: ActivatedRoute
  ) {
    this.isbn = ''
  }

  ngOnInit(): void {
    this.mostrarLibro = false
    this.route.params.subscribe({
      next: params => {
        this.isbn = params['isbn']
        this.mostrarSpinner = true
        this.mostrarNotificacionWarn = false
        this.recuperaInfoLibro()
      },
      error: () => {
        this.mostrarSpinner = false
        this.mostrarLibro = false
        // eslint-disable-next-line no-console
        console.log('No se recupero el isbn correctamente')
      },
    })
  }

  recuperaInfoLibro() {
    this.serviceApi.getLibro(this.isbn).subscribe({
      next: libro => {
        this.Libro = libro
        this.mostrarLibro = true
        this.mostrarSpinner = false
      },
      error: () => {
        // eslint-disable-next-line no-console
        console.log('No se cargaron los datos correctamente')
        this.mostrarSpinner = false
        this.mostrarLibro = false
      },
    })
  }
}
