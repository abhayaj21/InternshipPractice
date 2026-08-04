import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dashbordhome } from './dashbordhome';

describe('Dashbordhome', () => {
  let component: Dashbordhome;
  let fixture: ComponentFixture<Dashbordhome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dashbordhome],
    }).compileComponents();

    fixture = TestBed.createComponent(Dashbordhome);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
