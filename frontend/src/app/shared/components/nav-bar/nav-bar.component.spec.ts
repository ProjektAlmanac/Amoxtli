import { ComponentFixture, TestBed } from '@angular/core/testing'

import { NavBarComponent } from './nav-bar.component'
import { SharedModule } from '../../shared.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

describe('NavBarComponent', () => {
  let component: NavBarComponent
  let fixture: ComponentFixture<NavBarComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavBarComponent],
      imports: [SharedModule, NoopAnimationsModule],
    })
    fixture = TestBed.createComponent(NavBarComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
