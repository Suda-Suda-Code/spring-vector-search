import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private apiUrl = 'http://localhost:8080/v1/chat'; // Replace with your REST endpoint

  constructor(private http: HttpClient) { }

  sendMessage(message: string): Observable<any> {
    const body = {
      "query":"rasagulla with icecream"
    }
    return this.http.post(this.apiUrl, body);
  }
}
