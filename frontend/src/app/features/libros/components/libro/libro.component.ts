import { Component, Input } from '@angular/core'
import { DetallesLibro } from 'src/generated/openapi'

@Component({
  selector: 'app-libro',
  templateUrl: './libro.component.html',
  styleUrls: ['./libro.component.sass'],
})
export class LibroComponent {
  @Input()
  public libro!: DetallesLibro
}
