import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, JhiMainComponent, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: './admin/admin.module#AcademyProjectAdminModule'
        },
        {
          path: 'meetup',
          loadChildren: './meetup/meetup.module#MeetupModule'
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class AcademyProjectAppRoutingModule {}
