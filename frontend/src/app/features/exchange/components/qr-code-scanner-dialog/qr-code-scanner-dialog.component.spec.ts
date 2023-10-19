import { ComponentFixture, TestBed } from '@angular/core/testing'

import { QrCodeScannerDialogComponent } from './qr-code-scanner-dialog.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { ExchangeModule } from '../../exchange.module'

describe('QrCodeScannerDialogComponent', () => {
  let component: QrCodeScannerDialogComponent
  let fixture: ComponentFixture<QrCodeScannerDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QrCodeScannerDialogComponent],
      imports: [HttpClientTestingModule, ExchangeModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { exchangeId: 1, code: 'code' } },
      ],
    })
    fixture = TestBed.createComponent(QrCodeScannerDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
