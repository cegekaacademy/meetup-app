import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {HttpResponse} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class EventsService {

    constructor() {
    }

    getFutureEvents(): Observable<any> {
        return null;
    }

    getMyEvents(): Observable<any> {
        return null;
    }

    getPendingEvents(): Observable<any> {
        return null;
    }
}
