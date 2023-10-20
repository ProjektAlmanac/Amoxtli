import { AfterViewInit, Component, Inject, signal } from '@angular/core'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { DefaultService } from 'src/generated/openapi'
import { QrCodeDialogComponent } from '../qr-code-dialog/qr-code-dialog.component'
import { Html5QrcodeScanner } from 'html5-qrcode'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'

interface ExchangeData {
  exchangeId: number
  code: string
}

type State = 'success' | 'error' | 'pending'

@Component({
  selector: 'app-qr-code-scanner-dialog',
  templateUrl: './qr-code-scanner-dialog.component.html',
  styleUrls: ['./qr-code-scanner-dialog.component.sass'],
})
export class QrCodeScannerDialogComponent implements AfterViewInit {
  scanner!: Html5QrcodeScanner

  scanning = signal(true)

  state = signal<State>('pending')

  constructor(
    private userService: ServicioUsuario,
    private apiService: DefaultService,
    public dialogRef: MatDialogRef<QrCodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: object
  ) {}

  ngAfterViewInit() {
    this.scanner = new Html5QrcodeScanner(
      'qr-scanner',
      {
        fps: 10,
        qrbox: { width: 250, height: 250 },
      },
      false
    )

    this.scanner.render(this.onCodeScanned.bind(this), () => undefined)
  }

  onCodeScanned(code: string) {
    try {
      const obj = JSON.parse(code)
      if (typeof obj.exchangeId == 'number' && typeof obj.code == 'string') {
        this.scanner.pause()
        this.scanning.set(false)
        this.finalizeExchange(obj)
      }
      // eslint-disable-next-line no-empty
    } catch (error) {}
  }

  finalizeExchange(data: ExchangeData) {
    this.apiService
      .finalizarIntercambio(this.userService.id(), data.exchangeId, {
        codigo: data.code,
      })
      .subscribe({
        next: () => {
          this.state.set('success')
        },
        error: () => {
          this.state.set('error')
        },
      })
  }

  closeDialog() {
    this.dialogRef.close()
  }
}
