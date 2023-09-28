import { ComponentFixture, TestBed } from '@angular/core/testing'

import { HomePageComponent } from './home-page.component'
import { UserModule } from '../../user.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('HomeComponent', () => {
  let component: HomePageComponent
  let fixture: ComponentFixture<HomePageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomePageComponent],
      imports: [UserModule, NoopAnimationsModule, RouterTestingModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(HomePageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
