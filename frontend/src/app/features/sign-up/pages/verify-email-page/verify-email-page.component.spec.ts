import { ComponentFixture, TestBed } from '@angular/core/testing'

import { VerifyEmailPageComponent } from './verify-email-page.component'

describe('VerifyEmailPageComponent', () => {
  let component: VerifyEmailPageComponent
  let fixture: ComponentFixture<VerifyEmailPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyEmailPageComponent],
    })
    fixture = TestBed.createComponent(VerifyEmailPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
