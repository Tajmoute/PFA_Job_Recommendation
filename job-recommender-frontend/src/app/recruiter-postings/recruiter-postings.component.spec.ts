import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruiterPostingsComponent } from './recruiter-postings.component';

describe('RecruiterPostingsComponent', () => {
  let component: RecruiterPostingsComponent;
  let fixture: ComponentFixture<RecruiterPostingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecruiterPostingsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecruiterPostingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
