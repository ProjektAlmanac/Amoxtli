import { ComponentFixture, TestBed } from '@angular/core/testing'

import { QrCodeDialogComponent } from './qr-code-dialog.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'
import { ExchangeModule } from '../../exchange.module'

describe('QrCodeDialogComponent', () => {
  let component: QrCodeDialogComponent
  let fixture: ComponentFixture<QrCodeDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QrCodeDialogComponent],
      imports: [HttpClientTestingModule, ExchangeModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { exchangeId: 1, userId: 1 } },
      ],
    })
    fixture = TestBed.createComponent(QrCodeDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
