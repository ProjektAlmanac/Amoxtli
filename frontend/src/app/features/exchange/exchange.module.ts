import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { ExchangesPageComponent } from './pages/exchanges-page/exchanges-page.component'
import { ExchangeCardComponent } from './components/exchange-card/exchange-card.component'

import { MatCardModule } from '@angular/material/card'
import { MatButtonModule } from '@angular/material/button'
import { MatDividerModule } from '@angular/material/divider'
import { MatIconModule } from '@angular/material/icon'

@NgModule({
  declarations: [ExchangesPageComponent, ExchangeCardComponent],
  imports: [CommonModule, MatCardModule, MatButtonModule, MatDividerModule, MatIconModule],
})
export class ExchangeModule {}
