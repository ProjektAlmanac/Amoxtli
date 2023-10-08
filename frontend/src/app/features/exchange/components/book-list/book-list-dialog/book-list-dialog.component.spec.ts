import { ComponentFixture, TestBed } from '@angular/core/testing'

import { BookListDialogComponent } from './book-list-dialog.component'

describe('BookListDialogComponent', () => {
  let component: BookListDialogComponent
  let fixture: ComponentFixture<BookListDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookListDialogComponent],
    })
    fixture = TestBed.createComponent(BookListDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
