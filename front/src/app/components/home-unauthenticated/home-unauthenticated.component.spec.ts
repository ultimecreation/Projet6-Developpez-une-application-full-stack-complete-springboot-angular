import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUnauthenticatedComponent } from './home-unauthenticated.component';

describe('HomeUnauthenticatedComponent', () => {
  let component: HomeUnauthenticatedComponent;
  let fixture: ComponentFixture<HomeUnauthenticatedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeUnauthenticatedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeUnauthenticatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
