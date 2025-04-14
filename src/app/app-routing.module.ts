import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EtudesComponent } from './components/etudes/etudes.component';
import { ReactiveFormsModule } from '@angular/forms';


import { ProjectsComponent } from './components/projects/projects.component';
import { WeatherComponent } from './services/weather.component';

const routes: Routes = [

  { path: 'etudes', component: EtudesComponent },
  { path: 'weather', component: WeatherComponent },
  { path: 'projects', component: ProjectsComponent },
  { path: '', redirectTo: '/etudes', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    anchorScrolling: 'enabled',
    scrollPositionRestoration: 'enabled',
    onSameUrlNavigation: 'ignore', // Prevent Angular from resetting when navigating to #hash
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
