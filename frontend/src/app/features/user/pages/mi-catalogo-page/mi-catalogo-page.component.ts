import { Component } from '@angular/core'

@Component({
  selector: 'app-mi-catalogo-page',
  templateUrl: './mi-catalogo-page.component.html',
  styleUrls: ['./mi-catalogo-page.component.sass'],
})
export class MiCatalogoPageComponent {
  public readonly librosRegistrados = [
    { titulo: 'Libro 1', descripcion: 'Descripcion 1' },
    { titulo: 'Libro 2', descripcion: 'Descripcion 2' },
    { titulo: 'Libro 3', descripcion: 'Descripcion 3' },
  ]
}
