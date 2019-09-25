import {Component, ElementRef, OnInit, Renderer} from '@angular/core';
import {MeetupChallenge} from 'app/meetup-challenges/model/MeetupChallenge';
import {Router} from '@angular/router';
import {MeetupChallengesService} from 'app/meetup-challenges/meetup-challenges.service';
import {AccountService, IUser, LoginModalService, UserService} from 'app/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, Validators} from '@angular/forms';
import {Register} from 'app/account';
import {ChallengeCategory} from "app/meetup-challenges/model/ChallengeCategory";

@Component({
    selector: 'jhi-challenges-save-form',
    templateUrl: './challenges-save-form.component.html',
    styleUrls: ['./challenges-save-form.component.scss']
})
export class ChallengesSaveFormComponent implements OnInit {

    doNotMatch: string;
    error: string;
    success: boolean;
    modalRef: NgbModalRef;
    challenge: MeetupChallenge = new MeetupChallenge();
    title: string = "Create a challenge";
    user: IUser;
    challengeCategories: ChallengeCategory[] = [];
    selectedCategoryIndex: number = -1;

    saveForm = this.fb.group({
        startDate: ['', [Validators.required, Validators.pattern("((\\d{4})-(\\d{2})-(\\d{2})|(\\d{2})\\/(\\d{2})\\/(\\d{4}))")]],
        endDate: ['', [Validators.required, Validators.pattern("((\\d{4})-(\\d{2})-(\\d{2})|(\\d{2})\\/(\\d{2})\\/(\\d{4}))")]],
        status: ['', [Validators.required]],
        points: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
        description: ['', [Validators.required]],
        categoryName: ['', [Validators.required]]
    });

    constructor(
        private router: Router,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private fb: FormBuilder,
        private challengeService: MeetupChallengesService,
        private accountService: AccountService,
        private userService: UserService
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.getCurrentUser();
        this.getChallengeCategories();
    }

    getCurrentUser() {
        return this.accountService.identity().then((userIdentity) => {
            this.userService.find(userIdentity.login).toPromise()
                .then(
                    response => this.onUserFound(response.body));
        });
    }

    onUserFound(data) {
        this.user = data;
        this.challenge.creator = this.user;
    }

    getChallengeCategories() {
        this.challengeService.getAllChallengeCategories().subscribe((response) => {
            if (response) {
                this.challengeCategories = response;
            }
        }, () => {

        });
    }

    onSubmit() {
        console.log(this.selectedCategoryIndex);
        this.challenge.challengeCategory = this.challengeCategories[this.selectedCategoryIndex];
        this.challengeService.saveChallenge(this.challenge).subscribe(result => this.gotoChallengeList());
    }

    gotoChallengeList() {
        this.router.navigate(['/challenges/list']);
    }

    previousState() {
        window.history.back();
    }

}