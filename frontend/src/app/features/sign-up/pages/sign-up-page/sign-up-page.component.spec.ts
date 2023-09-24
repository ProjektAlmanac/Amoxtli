import { ComponentFixture, TestBed } from '@angular/core/testing'

import { SignUpPageComponent } from './sign-up-page.component'
import { SignUpModule } from '../../sign-up.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing'

describe('SignUpPageComponent', () => {
  let component: SignUpPageComponent
  let fixture: ComponentFixture<SignUpPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SignUpPageComponent],
      imports: [SignUpModule, NoopAnimationsModule, HttpClientTestingModule, RouterTestingModule],
    })
    fixture = TestBed.createComponent(SignUpPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
