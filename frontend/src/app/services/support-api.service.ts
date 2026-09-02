import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssistCustomerRequest, AssistCustomerResult } from '../models/support.model';

@Injectable({ providedIn: 'root'})
export class SupportApiService {
  private readonly http = inject(HttpClient);

  private readonly baseUrl = 'http://localhost:8080/api/v1/support/assist';

  assist(request: AssistCustomerRequest): Observable<AssistCustomerResult> {
    return this.http.post<AssistCustomerResult>(this.baseUrl, request);
  }
}
