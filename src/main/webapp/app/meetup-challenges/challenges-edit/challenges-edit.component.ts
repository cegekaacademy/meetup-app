import {Component, ElementRef, OnInit, Renderer} from '@angular/core';
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, Validators} from "@angular/forms";
import {AccountService, IUser, LoginModalService, UserService} from "app/core";
import {Register} from "app/account";
import {MeetupChallenge} from "app/meetup-challenges/model/MeetupChallenge";
import {MeetupChallengesService} from "app/meetup-challenges/meetup-challenges.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ChallengeCategory} from "app/meetup-challenges/model/ChallengeCategory";

@Component({
  selector: 'jhi-challenges-edit',
  templateUrl: './challenges-edit.component.html',
  styleUrls: ['./challenges-edit.component.scss']
})
export class ChallengesEditComponent implements OnInit {

  doNotMatch: string;
  error: string;
  success: boolean;
  modalRef: NgbModalRef;
  challenge: MeetupChallenge = new MeetupChallenge();
  user: IUser;
  challengeId: number = null;
  challengeCategories: ChallengeCategory[];
  challengeCategoryIndex: number = -1;

  editForm = this.fb.group({
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
      private userService: UserService,
      private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.success = false;

    this.route.params.subscribe(params => {
      this.challengeId = params['id'];
    });

    this.getCurrentUser();
    this.getChallengeCategories();
    this.initChallenge();
  }

  initChallenge() {
    this.challengeService.getChallengeById(this.challengeId).subscribe((data)=>{
      this.challenge = data.body;
      this.getInitialChallengeCategoryIndex(this.challenge.challengeCategory);
    })
  }

  getCurrentUser() {
    return this.accountService.identity().then((userIdentity) => {
      this.userService.find(userIdentity.login).toPromise()
          .then(
              response => this.onUserFound(response.body));
    })
  }

  getChallengeCategories() {
    this.challengeService.getAllChallengeCategories().subscribe((response) => {
      if (response) {
        this.challengeCategories = response;
        this.getInitialChallengeCategoryIndex(this.challenge.challengeCategory);
      }
    }, () => {

    });
  }

  getInitialChallengeCategoryIndex(initialChallengeCategory: ChallengeCategory){
    if(initialChallengeCategory != undefined && this.challengeCategories != undefined) {
      for (let c of this.challengeCategories) {
        if (c.id == initialChallengeCategory.id) {
          this.challengeCategoryIndex = this.challengeCategories.indexOf(c);
          break;
        }
      }
    }
  }

  onUserFound(data) {
    this.user = data;
    this.challenge.creator = this.user;
  }

  onSubmit(){
      this.challenge.challengeCategory = this.challengeCategories[this.challengeCategoryIndex];
      this.challengeService.updateChallenge(this.challenge, this.challengeId).subscribe(result => this.gotoChallengeList());
  }

  gotoChallengeList() {
    this.router.navigate(['/challenges/list']);
  }

  previousState() {
    window.history.back();
  }

}
