import { ComponentFixture, TestBed } from '@angular/core/testing'

import { ExchangesPageComponent } from './exchanges-page.component'
import { ExchangeModule } from '../../exchange.module'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('ExchangesPageComponent', () => {
  let component: ExchangesPageComponent
  let fixture: ComponentFixture<ExchangesPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExchangesPageComponent],
      imports: [ExchangeModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(ExchangesPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
