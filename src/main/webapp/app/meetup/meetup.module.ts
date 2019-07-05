import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MeetupRoutingModule } from './meetup-routing.module';
import { MeetupComponent } from './meetup-main/meetup.component';
import { UserProfileComponent } from 'app/meetup/user-profile/user-profile.component';

@NgModule({
  declarations: [MeetupComponent, UserProfileComponent],
  imports: [CommonModule, MeetupRoutingModule]
})
export class MeetupModule {}
