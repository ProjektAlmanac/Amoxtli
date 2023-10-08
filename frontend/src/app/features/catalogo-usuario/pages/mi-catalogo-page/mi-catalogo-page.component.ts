import { Component, OnInit } from '@angular/core'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { DefaultService, LibrosUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-mi-catalogo-page',
  templateUrl: './mi-catalogo-page.component.html',
  styleUrls: ['./mi-catalogo-page.component.sass'],
})
export class MiCatalogoPageComponent implements OnInit {
  public librosUsuario!: LibrosUsuario
  public mostrarSpinner = false

  constructor(
    private serviceApi: DefaultService,
    private serviceUsuario: ServicioUsuario
  ) {}

  ngOnInit(): void {
    this.mostrarSpinner = true
    this.recuperaLibrosUsuario()
  }

  recuperaLibrosUsuario() {
    const idUsuario = this.serviceUsuario.id.value
    this.serviceApi.getLibrosUsuario(idUsuario).subscribe(libros => {
      this.librosUsuario = libros
      this.mostrarSpinner = false
    })
  }
}
