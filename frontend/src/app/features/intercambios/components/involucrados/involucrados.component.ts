import { Component, Input, OnInit } from '@angular/core'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { DefaultService, Dueno, PerfilUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-involucrados',
  templateUrl: './involucrados.component.html',
  styleUrls: ['./involucrados.component.sass'],
})
export class InvolucradosComponent implements OnInit {
  @Input()
  public aceptante!: Dueno

  public ofertante!: PerfilUsuario

  constructor(
    private apiService: DefaultService,
    private servicioUsuario: ServicioUsuario
  ) {}

  ngOnInit(): void {
    const idUsuario = this.servicioUsuario.id.value
    this.apiService.getUsuario(idUsuario).subscribe(data => (this.ofertante = data))
  }
}
