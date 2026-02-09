import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthRedirect } from './auth-redirect';

describe('AuthRedirect', () => {
  let component: AuthRedirect;
  let fixture: ComponentFixture<AuthRedirect>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthRedirect]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthRedirect);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
