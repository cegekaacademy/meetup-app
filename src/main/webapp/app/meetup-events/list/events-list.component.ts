import {Component, OnInit} from '@angular/core';
import {MeetupEvent} from "app/meetup-events/model/MeetupEvent";
import {EventsService} from "app/meetup-events/events.service";
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-events-list',
    templateUrl: './events-list.component.html',
    styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent implements OnInit {

    events: MeetupEvent[] = [];
    isFutureEventsActive: boolean = true;
    isMyEventsActive: boolean = false;
    isGetPendingActive: boolean = false;

    constructor(private service: EventsService, private router: Router) {
    }

    ngOnInit() {
        this.getEvents();
    }

    getEvents() {
        if (this.isFutureEventsActive) {
            this.service.getFutureEvents().subscribe((response) => {
                if (response) {
                    this.events = response;
                }
            }, () => {

            });
        } else if (this.isGetPendingActive) {
            this.service.getPendingEvents().subscribe((response) => {
                if (response) {
                    this.events = response;
                }
            }, () => {

            });
        } else {
            this.service.getMyEvents().subscribe((response) => {
                if (response) {
                    this.events = response;
                }
            }, () => {

            });
        }
    }

    setTabFutureEvents() {
        if (!this.isFutureEventsActive) {
            this.isFutureEventsActive = true;
            this.isGetPendingActive=false;
            this.isMyEventsActive=false;
        }
    }

    setTabMyEvents() {
        if (!this.isMyEventsActive) {
            this.isMyEventsActive = true;
            this.isFutureEventsActive = false;
            this.isGetPendingActive=false;
        }
    }


    setTabPendingEvents() {
        if (!this.isGetPendingActive) {
            this.isGetPendingActive = true;
            this.isFutureEventsActive = false;
            this.isMyEventsActive=false;
        }
    }

    onEventClick(event){
        this.router.navigate(['events/edit/' + event.id]);
    }
}
