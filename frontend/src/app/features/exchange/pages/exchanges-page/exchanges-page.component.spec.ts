import { ComponentFixture, TestBed } from '@angular/core/testing'

import { ExchangesPageComponent } from './exchanges-page.component'

describe('ExchangesPageComponent', () => {
  let component: ExchangesPageComponent
  let fixture: ComponentFixture<ExchangesPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExchangesPageComponent],
    })
    fixture = TestBed.createComponent(ExchangesPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
