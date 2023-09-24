import { ComponentFixture, TestBed } from '@angular/core/testing'

import { VerifyEmailFormComponent } from './verify-email-form.component'
import { SignUpModule } from '../../sign-up.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

describe('VerifyEmailFormComponent', () => {
  let component: VerifyEmailFormComponent
  let fixture: ComponentFixture<VerifyEmailFormComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyEmailFormComponent],
      imports: [SignUpModule, NoopAnimationsModule],
    })
    fixture = TestBed.createComponent(VerifyEmailFormComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
