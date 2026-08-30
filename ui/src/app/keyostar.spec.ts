import { TestBed } from '@angular/core/testing';
import { Keyostar } from './keyostar';

describe('Keyostar', () => {
  let service: Keyostar;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Keyostar);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
