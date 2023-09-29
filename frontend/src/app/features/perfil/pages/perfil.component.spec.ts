import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PerfilComponent } from './perfil.component'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('PerfilComponent', () => {
  let component: PerfilComponent
  let fixture: ComponentFixture<PerfilComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PerfilComponent],
      imports: [NoopAnimationsModule, RouterTestingModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(PerfilComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
