import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobRecommendationsComponent } from './job-recommendations.component';

describe('JobRecommendationsComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JobRecommendationsComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(JobRecommendationsComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'job-recommender-frontend' title`, () => {
    const fixture = TestBed.createComponent(JobRecommendationsComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('job-recommender-frontend');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(JobRecommendationsComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, job-recommender-frontend');
  });
});

