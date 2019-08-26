import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    @Output() isMenuExpanded: EventEmitter<any> = new EventEmitter<any>();

    isExpanded = true;

    constructor() {
    }

    ngOnInit() {
    }

    toggleMenu() {
        this.isExpanded = !this.isExpanded;
        this.isMenuExpanded.emit({isExpanded: this.isExpanded});
    }
}
