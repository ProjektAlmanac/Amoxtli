import { Component, OnInit, signal } from '@angular/core'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { DefaultService, Intercambio } from 'src/generated/openapi'

@Component({
  selector: 'app-exchanges-page',
  templateUrl: './exchanges-page.component.html',
  styleUrls: ['./exchanges-page.component.sass'],
})
export class ExchangesPageComponent implements OnInit {
  readonly pendingExchanges = signal<Intercambio[] | undefined>(undefined)
  readonly otherExchanges = signal<Intercambio[] | undefined>(undefined)

  constructor(
    private apiService: DefaultService,
    private userSservice: ServicioUsuario
  ) {}

  ngOnInit() {
    const id = this.userSservice.id()
    this.apiService.getIntercambios(id).subscribe({
      next: response => {
        this.pendingExchanges.set(
          response.intercambios?.filter(exchange => exchange.estado === 'Pendiente')
        )
        this.otherExchanges.set(
          response.intercambios?.filter(exchange => exchange.estado !== 'Pendiente')
        )
      },
      error: error => {
        // eslint-disable-next-line no-console
        console.error(error)
      },
    })
  }
}
