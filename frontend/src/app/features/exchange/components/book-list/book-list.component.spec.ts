import { ComponentFixture, TestBed } from '@angular/core/testing'

import { BookListComponent } from './book-list.component'
import { ExchangeModule } from '../../exchange.module'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('BookListComponent', () => {
  let component: BookListComponent
  let fixture: ComponentFixture<BookListComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookListComponent],
      imports: [ExchangeModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(BookListComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
