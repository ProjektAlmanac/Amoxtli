import { Component, Input } from '@angular/core'
import { Dueno, PerfilUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-involucrados',
  templateUrl: './involucrados.component.html',
  styleUrls: ['./involucrados.component.sass'],
})
export class InvolucradosComponent {
  @Input()
  public aceptante!: Dueno

  @Input()
  public ofertante!: PerfilUsuario
}
