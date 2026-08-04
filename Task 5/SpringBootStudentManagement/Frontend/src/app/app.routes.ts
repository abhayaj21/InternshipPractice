import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { Pagenotfound } from './pagenotfound/pagenotfound';
import { AuthGuard } from './auth/auth-guard';
import { Dashbordhome } from './dashboard/dashbordhome/dashbordhome';
import { Dashbordaddstudents } from './dashboard/dashbordaddstudents/dashbordaddstudents';
import { Dashboardviewstudent } from './dashboard/dashboardviewstudent/dashboardviewstudent';
import { Departments } from './dashboard/departments/departments';

export const routes: Routes = [
  {
    path: "dashboard",
    component:Dashboard,
    children: [
      {
        path:'',
        component: Dashbordhome
      },
      {
        path: 'student/add',
        component: Dashbordaddstudents
      },
      {
        path: 'student/list',
        component: Dashboardviewstudent
      },
      {
        path: 'departments',
        component: Departments
      }
    ],
    canActivate:[AuthGuard]
  },
  {
    path: "**",
    component:Pagenotfound
  }
];
