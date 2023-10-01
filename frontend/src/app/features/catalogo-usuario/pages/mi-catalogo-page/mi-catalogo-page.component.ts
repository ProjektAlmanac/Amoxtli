import { Component, OnInit } from '@angular/core'
import { DefaultService, LibrosUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-mi-catalogo-page',
  templateUrl: './mi-catalogo-page.component.html',
  styleUrls: ['./mi-catalogo-page.component.sass'],
})
export class MiCatalogoPageComponent implements OnInit {
  public librosUsuario!: LibrosUsuario
  public mostrarSpinner = false

  constructor(private serviceApi: DefaultService) {}

  ngOnInit(): void {
    this.mostrarSpinner = true
    this.recuperaLibrosUsuario()
  }

  recuperaLibrosUsuario() {
    this.serviceApi.getLibrosUsuario(1).subscribe(libros => {
      this.librosUsuario = libros
      this.mostrarSpinner = false
    })
  }
}
