import {User} from "app/core";

export class MeetupEvent{
    id: number;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    owner: User;
    notes: string;
    isPublic: boolean;
    address: string;
}