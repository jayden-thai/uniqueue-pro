import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderDirectory } from './provider-directory';

describe('ProviderDirectory', () => {
  let component: ProviderDirectory;
  let fixture: ComponentFixture<ProviderDirectory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProviderDirectory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProviderDirectory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
