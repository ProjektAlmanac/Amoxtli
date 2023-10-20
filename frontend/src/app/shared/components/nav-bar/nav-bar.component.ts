import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.sass'],
})
export class NavBarComponent {
  constructor(
    public userService: ServicioUsuario,
    private router: Router
  ) {}

  logout() {
    this.userService.logout()
    this.router.navigate(['/'])
  }
  login() {
    this.router.navigate(['/login'])
  }
}
