import { ComponentFixture, TestBed } from '@angular/core/testing'

import { BookListDialogComponent } from './book-list-dialog.component'
import { ExchangeModule } from '../../exchange.module'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog'

describe('BookListDialogComponent', () => {
  let component: BookListDialogComponent
  let fixture: ComponentFixture<BookListDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookListDialogComponent],
      imports: [ExchangeModule, HttpClientTestingModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { userId: 1 } },
      ],
    })
    fixture = TestBed.createComponent(BookListDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
