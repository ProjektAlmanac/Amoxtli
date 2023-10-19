import {
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  OnDestroy,
  ViewChild,
  signal,
} from '@angular/core'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { DefaultService } from 'src/generated/openapi'
//@ts-expect-error QRious is not typed
import QRious from 'qrious'
import { HttpErrorResponse } from '@angular/common/http'

export interface DialogData {
  exchangeId: number
  userId: number
}

@Component({
  selector: 'app-qr-code-dialog',
  templateUrl: './qr-code-dialog.component.html',
  styleUrls: ['./qr-code-dialog.component.sass'],
})
export class QrCodeDialogComponent implements AfterViewInit, OnDestroy {
  @ViewChild('qr')
  qrCanvas!: ElementRef<HTMLCanvasElement>

  qrCode!: QRious

  loading = signal(true)

  done = signal(false)

  intervalId = 0

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
    this.intervalId = setInterval(this.checkIfDone.bind(this), 1000)
  }

  ngOnDestroy() {
    clearInterval(this.intervalId)
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

  private checkIfDone() {
    this.apiService.getIntercambio(this.data.userId, this.data.exchangeId).subscribe({
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.done.set(true)
          clearInterval(this.intervalId)
        }
      },
    })
  }

  closeDialog() {
    this.dialogRef.close()
  }
}
