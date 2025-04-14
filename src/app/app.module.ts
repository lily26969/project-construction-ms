import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { EtudesComponent } from './components/etudes/etudes.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MapComponent } from './components/map/map.component';
import { CommonModule } from '@angular/common';
import { WeatherComponent } from './services/weather.component';






@NgModule({
  declarations: [
    AppComponent,
    EtudesComponent,
    ProjectsComponent,
    MapComponent,
    WeatherComponent
    ],
  imports: [
    CommonModule, // ✅ Fix NgIf & NgFor
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule, // ✅ Add this
    AppRoutingModule,
    FormsModule,
    RouterModule.forRoot([]) // <-- Ensure RouterModule is included

  ],
  providers: [

      // Add WeatherService here

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
