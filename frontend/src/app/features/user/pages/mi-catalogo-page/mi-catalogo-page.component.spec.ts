import { ComponentFixture, TestBed } from '@angular/core/testing'

import { MiCatalogoPageComponent } from './mi-catalogo-page.component'

describe('MiCatalogoPageComponent', () => {
  let component: MiCatalogoPageComponent
  let fixture: ComponentFixture<MiCatalogoPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MiCatalogoPageComponent],
    })
    fixture = TestBed.createComponent(MiCatalogoPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
