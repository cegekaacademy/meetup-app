import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {SERVER_API_URL} from "app/app.constants";
import {MeetupChallenge} from "app/meetup-challenges/model/MeetupChallenge";
import {ChallengeCategory} from "app/meetup-challenges/model/ChallengeCategory";

@Injectable({
    providedIn: 'root'
})
export class MeetupChallengesService {

    constructor(private http: HttpClient) {
    }

    //TODO: sort the array by method scope
    getFutureChallenges(id: number): Observable<any> {
        return this.getAllChallengesByUserId(id);
    }

    getMyChallenges(id: number): Observable<any> {
        return this.http.get(SERVER_API_URL + "challenge/creator/" + id, {observe: 'response'});
}

    //TODO: sort the array by method scope
    getPendingChallenges(id: number): Observable<any> {
        return this.getAllChallengesByUserId(id);
    }

    getChallengeById(id: number): Observable<any> {
        return this.http.get(SERVER_API_URL + "challenge/" + id, {observe: 'response'});
    }

    getAllChallengesByUserId(id: number): Observable<any> {
        return this.http.get(SERVER_API_URL + "challenge/user/" + id, {observe: 'response'});
    }

    deleteChallenge(id: number): Observable<any> {
        return this.http.delete(SERVER_API_URL + "/challenge/" + id, {observe: 'response'});
    }

    saveChallenge(challenge: MeetupChallenge) {
        return this.http.post<MeetupChallenge>(SERVER_API_URL + "/challenge", challenge);
    }

    updateChallenge(challenge: MeetupChallenge, id:number){
        return this.http.put<MeetupChallenge>(SERVER_API_URL + "/challenge/" + id, challenge);
    }

    getAllChallengeCategories():Observable<any>{
        return this.http.get<ChallengeCategory>(SERVER_API_URL + "/challengeCategory/all");
    }
}
