import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RtlComponent } from './rtl.component';

describe('RtlComponent', () => {
  let component: RtlComponent;
  let fixture: ComponentFixture<RtlComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RtlComponent]
    });
    fixture = TestBed.createComponent(RtlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
