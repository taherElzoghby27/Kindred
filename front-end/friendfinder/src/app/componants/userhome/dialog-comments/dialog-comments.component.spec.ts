import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogCommentsComponent } from './dialog-comments.component';

describe('DialogCommentsComponent', () => {
  let component: DialogCommentsComponent;
  let fixture: ComponentFixture<DialogCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogCommentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
