import { ComponentFixture, TestBed } from '@angular/core/testing'

import { BookDialogComponent } from './book-dialog.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { ExchangeModule } from '../../exchange.module'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'

describe('BookDialogComponent', () => {
  let component: BookDialogComponent
  let fixture: ComponentFixture<BookDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookDialogComponent],
      imports: [HttpClientTestingModule, ExchangeModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { isbn: '' } },
      ],
    })
    fixture = TestBed.createComponent(BookDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
