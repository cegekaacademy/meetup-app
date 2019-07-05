import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserProfileComponent } from 'app/meetup/user-profile/user-profile.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'user-profile',
        component: UserProfileComponent,
        data: {
          pageTitle: 'User Profile'
        }
      }
    ],
    data: {
      authorities: [],
      pageTitle: 'Meet UP!'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MeetupRoutingModule {}
