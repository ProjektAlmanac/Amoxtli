import { ComponentFixture, TestBed } from '@angular/core/testing'

import { ExchangeCardComponent } from './exchange-card.component'
import { ExchangeModule } from '../../exchange.module'

describe('ExchangeCardComponent', () => {
  let component: ExchangeCardComponent
  let fixture: ComponentFixture<ExchangeCardComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExchangeCardComponent],
      imports: [ExchangeModule],
    })
    fixture = TestBed.createComponent(ExchangeCardComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
