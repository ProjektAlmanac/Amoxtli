import { ComponentFixture, TestBed } from '@angular/core/testing'

import { MiCatalogoPageComponent } from './mi-catalogo-page.component'
import { CatalogoUsuarioModule } from '../../catalogo-usuario.module'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('MiCatalogoPageComponent', () => {
  let component: MiCatalogoPageComponent
  let fixture: ComponentFixture<MiCatalogoPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MiCatalogoPageComponent],
      imports: [
        CatalogoUsuarioModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
      ],
    })
    fixture = TestBed.createComponent(MiCatalogoPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
