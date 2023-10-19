import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { HomePageComponent } from './features/catalogo-usuario/pages/home-page/home-page.component'
import { MiCatalogoPageComponent } from './features/catalogo-usuario/pages/mi-catalogo-page/mi-catalogo-page.component'
import { SignUpPageComponent } from './features/sign-up/pages/sign-up-page/sign-up-page.component'
import { VerifyEmailPageComponent } from './features/sign-up/pages/verify-email-page/verify-email-page.component'
import { WelcomePageComponent } from './features/sign-up/pages/welcome-page/welcome-page.component'
import { MostrarLibrosComponent } from './features/libros/pages/mostrar-libros/mostrar-libros.component'
import { DetallesLibroComponent } from './features/libros/components/detalles-libro/detalles-libro.component'
import { AgregarLibroComponent } from './features/catalogo-usuario/components/agregar-libro/agregar-libro.component'
import { NavBarComponent } from './shared/components/nav-bar/nav-bar.component'
import { NotFoundComponent } from './core/pages/not-found/not-found.component'
import { PerfilComponent } from './features/perfil/pages/perfil.component'
import { LoginPageComponent } from './features/login/pages/login-page/login-page.component'
import { LibroPageComponent } from './features/libros/pages/libro-page/libro-page.component'

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
    path: 'login',
    component: LoginPageComponent,
  },
  {
    path: '',
    component: NavBarComponent,
    children: [
      { path: 'home', component: HomePageComponent },
      { path: 'miCatalogo', component: MiCatalogoPageComponent },
      { path: 'agregarLibro', component: AgregarLibroComponent },
      { path: 'perfil', component: PerfilComponent },

      { path: 'libros', component: MostrarLibrosComponent },
      { path: 'detallesLibro/:isbn', component: DetallesLibroComponent },

      { path: 'libro', component: LibroPageComponent },
    ],
  },
  // Página no encontrada
  {
    path: '**',
    pathMatch: 'full',
    component: NotFoundComponent,
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
