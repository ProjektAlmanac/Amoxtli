import { ComponentFixture, TestBed } from '@angular/core/testing'

import { WelcomePageComponent } from './welcome-page.component'
import { SignUpModule } from '../../sign-up.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'

describe('WelcomePageComponent', () => {
  let component: WelcomePageComponent
  let fixture: ComponentFixture<WelcomePageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WelcomePageComponent],
      imports: [SignUpModule, NoopAnimationsModule, RouterTestingModule],
    })
    fixture = TestBed.createComponent(WelcomePageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
