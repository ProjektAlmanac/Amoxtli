import { Component, OnInit, signal } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import {
  Aceptante,
  DefaultService,
  EstadoIntercambio,
  LibroRegistradoConDetalles,
  Ofertante,
} from 'src/generated/openapi'
import { BookListDialogComponent } from '../../components/book-list-dialog/book-list-dialog.component'
import { lastValueFrom } from 'rxjs'
import { QrCodeDialogComponent } from '../../components/qr-code-dialog/qr-code-dialog.component'
import { QrCodeScannerDialogComponent } from '../../components/qr-code-scanner-dialog/qr-code-scanner-dialog.component'

@Component({
  selector: 'app-exchanges-page',
  templateUrl: './exchanges-page.component.html',
  styleUrls: ['./exchanges-page.component.sass'],
})
export class ExchangesPageComponent implements OnInit {
  readonly pendingExchanges = signal<Exchange[] | undefined>(undefined)
  readonly otherExchanges = signal<Exchange[] | undefined>(undefined)

  readonly userId = this.userService.id

  constructor(
    private apiService: DefaultService,
    private userService: ServicioUsuario,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getExchanges(this.userId())
  }

  async getExchanges(userId: number) {
    const exchangeList = await lastValueFrom(this.apiService.getIntercambios(userId))
    if (exchangeList === null) {
      this.pendingExchanges.set([])
      this.otherExchanges.set([])
      return
    }
    const exchangePromises =
      exchangeList.intercambios?.map(async exchange => {
        const offeringBook = await this.getBookDetails(exchange.libroOfertante?.isbn)
        const acceptingBook = await this.getBookDetails(exchange.libroAceptante.isbn)
        return {
          id: exchange.id,
          state: exchange.estado,
          offeringBook: offeringBook,
          acceptingBook: acceptingBook,
          offeringUser: exchange.ofertante,
          acceptingUser: exchange.aceptante,
        } satisfies Exchange
      }) ?? []
    const exchanges = await Promise.all(exchangePromises)
    this.pendingExchanges.set(
      exchanges.filter(
        exchange => exchange.state === 'Pendiente' && exchange.offeringUser.id !== userId
      )
    )
    this.otherExchanges.set(exchanges.filter(exchange => exchange.state !== 'Pendiente'))
  }

  async showBookList(exchange: Exchange) {
    const dialogRef = this.dialog.open(BookListDialogComponent, {
      data: { userId: exchange.offeringUser.id },
    })
    const result = (await lastValueFrom(dialogRef.afterClosed())) as
      | LibroRegistradoConDetalles
      | undefined
    if (result === undefined) return
    this.apiService
      .aceptarIntercambio(exchange.acceptingUser.id, exchange.id, {
        idLibro: result.id,
      })
      .subscribe({
        // eslint-disable-next-line no-console
        error: err => console.error(err),
      })

    exchange.state = 'Aceptado'
    exchange.offeringBook = result
    this.otherExchanges.mutate(exchanges => exchanges?.unshift(exchange))
    this.pendingExchanges.update(exchanges => exchanges?.filter(e => e.id !== exchange.id))
  }

  private async getBookDetails(isbn: string) {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    if (isbn === undefined) return Promise.resolve(undefined!)
    return lastValueFrom(this.apiService.getDetallesLibro(isbn))
  }

  acceptExchange(exchange: Exchange) {
    this.dialog.open(QrCodeDialogComponent, {
      data: { exchangeId: exchange.id, userId: this.userId() },
    })
  }

  openQrCodeScanner() {
    this.dialog.open(QrCodeScannerDialogComponent)
  }
}

interface Exchange {
  id: number
  state: EstadoIntercambio
  offeringUser: Ofertante
  acceptingUser: Aceptante
  offeringBook?: Book
  acceptingBook: Book
}

interface Book {
  titulo: string
  autor: string
  isbn: string
  urlPortada: string
}
