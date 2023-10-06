import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { HomePageComponent } from './features/user/pages/home-page/home-page.component'
import { MiCatalogoPageComponent } from './features/user/pages/mi-catalogo-page/mi-catalogo-page.component'
import { SignUpPageComponent } from './features/sign-up/pages/sign-up-page/sign-up-page.component'
import { VerifyEmailPageComponent } from './features/sign-up/pages/verify-email-page/verify-email-page.component'
import { WelcomePageComponent } from './features/sign-up/pages/welcome-page/welcome-page.component'

import { AgregarLibroComponent } from './features/user/components/agregar-libro/agregar-libro.component'
import { NavBarComponent } from './shared/components/nav-bar/nav-bar.component'
import { PerfilComponent } from './features/perfil/pages/perfil.component'
import { MostrarLibrosComponent } from './features/libros/pages/mostrar-libros/mostrar-libros.component'

const routes: Routes = [
  {
    path: 'signup',
    component: SignUpPageComponent,
  },
  {
    path: 'signup/verify',
    component: VerifyEmailPageComponent,
  },
  {
    path: 'welcome',
    component: WelcomePageComponent,
  },

  {
    path: '',
    component: NavBarComponent,
    children: [
      {
        path: 'home',
        component: HomePageComponent,
      },
      {
        path: 'miCatalogo',
        component: MiCatalogoPageComponent,
      },
      {
        path: 'agregarLibro',
        component: AgregarLibroComponent,
      },
      { path: 'perfil', component: PerfilComponent },
      { path: 'libros', component: MostrarLibrosComponent },
    ],
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
