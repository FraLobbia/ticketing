import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketCorrespondenceComponent } from './ticket-correspondence.component';

describe('TicketCorrespondenceComponent', () => {
  let component: TicketCorrespondenceComponent;
  let fixture: ComponentFixture<TicketCorrespondenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TicketCorrespondenceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketCorrespondenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
