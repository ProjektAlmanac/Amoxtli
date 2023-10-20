import { Component } from '@angular/core'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'

@Component({
  selector: 'app-authenticated',
  templateUrl: './authenticated.component.html',
  styleUrls: ['./authenticated.component.sass'],
})
export class AuthenticatedComponent {
  constructor(public userService: ServicioUsuario) {}
}
