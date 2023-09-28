import { Component, OnInit } from '@angular/core'
import { DefaultService, LibrosUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-mi-catalogo-page',
  templateUrl: './mi-catalogo-page.component.html',
  styleUrls: ['./mi-catalogo-page.component.sass'],
})
export class MiCatalogoPageComponent implements OnInit {
  public librosUsuario!: LibrosUsuario

  constructor(private serviceApi: DefaultService) {}

  ngOnInit(): void {
    this.recuperaLibrosUsuario()
  }

  recuperaLibrosUsuario() {
    this.serviceApi.getLibrosUsuario(1).subscribe(libros => {
      this.librosUsuario = libros
    })
  }
}
