import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dashbordaddstudents } from './dashbordaddstudents';

describe('Dashbordaddstudents', () => {
  let component: Dashbordaddstudents;
  let fixture: ComponentFixture<Dashbordaddstudents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dashbordaddstudents],
    }).compileComponents();

    fixture = TestBed.createComponent(Dashbordaddstudents);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
