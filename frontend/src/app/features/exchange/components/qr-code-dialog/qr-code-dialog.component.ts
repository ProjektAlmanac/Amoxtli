import { AfterViewInit, Component, ElementRef, Inject, ViewChild, signal } from '@angular/core'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { DefaultService } from 'src/generated/openapi'
//@ts-expect-error QRious is not typed
import QRious from 'qrious'

export interface DialogData {
  exchangeId: number
  userId: number
}

@Component({
  selector: 'app-qr-code-dialog',
  templateUrl: './qr-code-dialog.component.html',
  styleUrls: ['./qr-code-dialog.component.sass'],
})
export class QrCodeDialogComponent implements AfterViewInit {
  @ViewChild('qr')
  qrCanvas!: ElementRef<HTMLCanvasElement>

  qrCode!: QRious

  loading = signal(true)

  constructor(
    private apiService: DefaultService,
    public dialogRef: MatDialogRef<QrCodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  ngAfterViewInit() {
    this.qrCode = new QRious({
      element: this.qrCanvas.nativeElement,
      size: 250,
    })
    this.getExchangeCode()
  }

  private getExchangeCode() {
    this.apiService
      .getCodigoIntercambio(this.data.userId, this.data.exchangeId)
      .subscribe(response => {
        this.qrCode.value = JSON.stringify({
          exchangeId: this.data.exchangeId,
          code: response.codigo,
        })
        this.loading.set(false)
      })
  }

  closeDialog() {
    this.dialogRef.close()
  }
}
