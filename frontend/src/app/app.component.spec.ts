import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { AppComponent } from './app.component'
import { SharedModule } from './shared/shared.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { MatCardModule } from '@angular/material/card'

describe('AppComponent', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [MatCardModule, RouterTestingModule, SharedModule, NoopAnimationsModule],
      declarations: [AppComponent],
    })
  )

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent)
    const app = fixture.componentInstance
    expect(app).toBeTruthy()
  })
})
