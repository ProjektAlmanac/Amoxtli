import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { SignInPageComponent } from './features/sign-up/pages/sign-up-page/sign-up-page.component'
import { VerifyEmailPageComponent } from './features/sign-up/pages/verify-email-page/verify-email-page.component'
import { WelcomePageComponent } from './features/sign-up/pages/welcome-page/welcome-page.component'

const routes: Routes = [
  {
    path: 'signup',
    component: SignInPageComponent,
  },
  {
    path: 'signup/verify',
    component: VerifyEmailPageComponent,
  },
  {
    path: 'welcome',
    component: WelcomePageComponent,
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
