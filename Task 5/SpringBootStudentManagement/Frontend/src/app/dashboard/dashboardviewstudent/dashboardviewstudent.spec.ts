import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dashboardviewstudent } from './dashboardviewstudent';

describe('Dashboardviewstudent', () => {
  let component: Dashboardviewstudent;
  let fixture: ComponentFixture<Dashboardviewstudent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dashboardviewstudent],
    }).compileComponents();

    fixture = TestBed.createComponent(Dashboardviewstudent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
