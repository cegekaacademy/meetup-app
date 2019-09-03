import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {SERVER_API_URL} from "app/app.constants";
import {UserService} from "app/core";

@Injectable({
    providedIn: 'root'
})
export class EventsService {

    constructor(private http: HttpClient) {
    }

    getFutureEvents(id: number): Observable<any> {
        return this.getAllEventsByUserId(id);
    }

    getMyEvents(id: number): Observable<any> {
        return this.getAllEventsByUserId(id);
    }

    getPendingEvents(id: number): Observable<any> {
        return this.getAllEventsByUserId(id);
    }

    getEventById(id: number): Observable<any> {
        return null;
    }

    getAllEventsByUserId(id: number): Observable<any>{
        return this.http.get(SERVER_API_URL+ "event/user/"+id, { observe: 'response' });
    }
}
