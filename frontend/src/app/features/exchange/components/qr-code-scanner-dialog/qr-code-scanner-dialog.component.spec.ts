import { ComponentFixture, TestBed } from '@angular/core/testing'

import { QrCodeScannerDialogComponent } from './qr-code-scanner-dialog.component'

describe('QrCodeScannerDialogComponent', () => {
  let component: QrCodeScannerDialogComponent
  let fixture: ComponentFixture<QrCodeScannerDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QrCodeScannerDialogComponent],
    })
    fixture = TestBed.createComponent(QrCodeScannerDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
