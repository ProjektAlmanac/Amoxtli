import { ComponentFixture, TestBed } from '@angular/core/testing'

import { VerifyEmailFormComponent } from './verify-email-form.component'

describe('VerifyEmailFormComponent', () => {
  let component: VerifyEmailFormComponent
  let fixture: ComponentFixture<VerifyEmailFormComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyEmailFormComponent],
    })
    fixture = TestBed.createComponent(VerifyEmailFormComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
