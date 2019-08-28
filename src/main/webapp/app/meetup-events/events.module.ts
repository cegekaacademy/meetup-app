import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventsRoutingModule } from './events-routing.module';
import {EventsListComponent} from "app/meetup-events/list/events-list.component";
import { EventEditComponent } from './event-edit/event-edit.component';

@NgModule({
  declarations: [EventsListComponent, EventEditComponent],
  imports: [
    CommonModule,
    EventsRoutingModule
  ]
})
export class EventsModule { }
