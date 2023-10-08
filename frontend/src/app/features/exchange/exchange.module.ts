import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { ExchangesPageComponent } from './pages/exchanges-page/exchanges-page.component'
import { ExchangeCardComponent } from './components/exchange-card/exchange-card.component'

import { MatCardModule } from '@angular/material/card'
import { MatButtonModule } from '@angular/material/button'
import { MatDividerModule } from '@angular/material/divider'
import { MatIconModule } from '@angular/material/icon'
import { MatDialogModule } from '@angular/material/dialog'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { BookListComponent } from './components/book-list/book-list.component'
import { BookListDialogComponent } from './components/book-list/book-list-dialog/book-list-dialog.component'
import { QrCodeDialogComponent } from './components/qr-code-dialog/qr-code-dialog.component'

@NgModule({
  declarations: [
    ExchangesPageComponent,
    ExchangeCardComponent,
    BookListComponent,
    BookListDialogComponent,
    QrCodeDialogComponent,
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatDialogModule,
    MatProgressSpinnerModule,
  ],
})
export class ExchangeModule {}
