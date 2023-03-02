import { Injectable, Inject } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root"
})
export class MessageService {
  constructor(
    private http: HttpClient,
  ) {}
}
