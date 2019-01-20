import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { INotes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';

@Component({
    selector: 'jhi-notes-update',
    templateUrl: './notes-update.component.html'
})
export class NotesUpdateComponent implements OnInit {
    notes: INotes;
    isSaving: boolean;

    constructor(protected notesService: NotesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ notes }) => {
            this.notes = notes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.notes.id !== undefined) {
            this.subscribeToSaveResponse(this.notesService.update(this.notes));
        } else {
            this.subscribeToSaveResponse(this.notesService.create(this.notes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INotes>>) {
        result.subscribe((res: HttpResponse<INotes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
