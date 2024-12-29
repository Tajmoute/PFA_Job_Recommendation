import { TestBed } from '@angular/core/testing';

import { JobRecommendationService } from './job-recommendation.service';

describe('JobRecommendationService', () => {
  let service: JobRecommendationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobRecommendationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
