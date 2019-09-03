import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MeetupChallengesRoutingModule } from './meetup-challenges-routing.module';
import { ChallengesListComponent } from './challenges-list/challenges-list.component';
import { ChallengesEditComponent } from './challenges-edit/challenges-edit.component';

@NgModule({
  declarations: [ChallengesListComponent, ChallengesEditComponent],
  imports: [
    CommonModule,
    MeetupChallengesRoutingModule
  ]
})
export class MeetupChallengesModule { }
