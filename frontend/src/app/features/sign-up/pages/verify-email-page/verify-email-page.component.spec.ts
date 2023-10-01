import { ComponentFixture, TestBed } from '@angular/core/testing'

import { VerifyEmailPageComponent } from './verify-email-page.component'
import { SignUpModule } from '../../sign-up.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing'

describe('VerifyEmailPageComponent', () => {
  let component: VerifyEmailPageComponent
  let fixture: ComponentFixture<VerifyEmailPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyEmailPageComponent],
      imports: [SignUpModule, NoopAnimationsModule, HttpClientTestingModule, RouterTestingModule],
    })
    fixture = TestBed.createComponent(VerifyEmailPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
