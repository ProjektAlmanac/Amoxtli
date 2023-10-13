import { Component, EventEmitter, Input, Output } from '@angular/core'
import { MatSelectionListChange } from '@angular/material/list'
import { Dueno, PerfilUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-duenos',
  templateUrl: './duenos.component.html',
  styleUrls: ['./duenos.component.sass'],
})
export class DuenosComponent {
  @Input()
  public duenos!: Dueno[]

  @Input()
  public botonIntercambiar = true

  @Input()
  public ofertante!: PerfilUsuario

  @Output()
  public usuarioSeleccionado = new EventEmitter()

  @Output()
  public solicitarIntercambio = new EventEmitter<Dueno>()

  public botonIntercambio = true

  public duenoSeleccionado!: Dueno

  actualizarDuenoSeleccionado(event: MatSelectionListChange) {
    this.duenoSeleccionado = event.options[0].value
    this.usuarioSeleccionado.emit()
    this.botonIntercambio = false
  }

  solicitaIntercambio() {
    this.solicitarIntercambio.emit(this.duenoSeleccionado)
  }
}
