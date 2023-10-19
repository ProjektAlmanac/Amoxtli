import { Component, EventEmitter, Input, Output } from '@angular/core'
import { EstadoIntercambio } from 'src/generated/openapi'

@Component({
  selector: 'app-exchange-card',
  templateUrl: './exchange-card.component.html',
  styleUrls: ['./exchange-card.component.sass'],
})
export class ExchangeCardComponent {
  @Input({ required: true })
  imgLeft!: string

  @Input({ required: true })
  imgRight!: string

  @Input({ required: true })
  titleLeft!: string

  @Input({ required: true })
  titleRight!: string

  @Input({ required: true })
  state!: EstadoIntercambio

  @Input({ required: true })
  name!: string

  @Output()
  accept = new EventEmitter<void>()

  @Output()
  cancel = new EventEmitter<void>()
}
