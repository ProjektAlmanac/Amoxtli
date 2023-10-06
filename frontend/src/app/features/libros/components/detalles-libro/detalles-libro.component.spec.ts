import { ComponentFixture, TestBed } from '@angular/core/testing'

import { DetallesLibroComponent } from './detalles-libro.component'

describe('DetallesLibroComponent', () => {
  let component: DetallesLibroComponent
  let fixture: ComponentFixture<DetallesLibroComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetallesLibroComponent],
    })
    fixture = TestBed.createComponent(DetallesLibroComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
