import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestimonailComponent } from './testimonail.component';

describe('TestimonailComponent', () => {
  let component: TestimonailComponent;
  let fixture: ComponentFixture<TestimonailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestimonailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestimonailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
