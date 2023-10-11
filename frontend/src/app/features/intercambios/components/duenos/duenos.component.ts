import { Component, Input } from '@angular/core'
import { MatSelectionListChange } from '@angular/material/list'
import { Dueno } from 'src/generated/openapi'

@Component({
  selector: 'app-duenos',
  templateUrl: './duenos.component.html',
  styleUrls: ['./duenos.component.sass'],
})
export class DuenosComponent {
  @Input()
  public duenos!: Dueno[]

  public duenoSeleccionado!: Dueno

  actualizarDuenoSeleccionado(event: MatSelectionListChange) {
    this.duenoSeleccionado = event.options[0].value
  }
}
