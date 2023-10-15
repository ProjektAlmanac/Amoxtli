import { ComponentFixture, TestBed } from '@angular/core/testing'

import { IntercambioPageComponent } from './intercambio-page.component'

describe('IntercambioPageComponent', () => {
  let component: IntercambioPageComponent
  let fixture: ComponentFixture<IntercambioPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IntercambioPageComponent],
    })
    fixture = TestBed.createComponent(IntercambioPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
