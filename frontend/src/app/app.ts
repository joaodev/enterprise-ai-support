import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  signal
} from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs';

import { AssistCustomerResult } from './models/support.model';
import { SupportApiService } from './services/support-api.service';

@Component({
  selector: 'app-root',
  imports: [ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  private readonly formBuilder = inject(FormBuilder);
  private readonly supportApi = inject(SupportApiService);
  private readonly destroyRef = inject(DestroyRef);

  readonly loading = signal(false);
  readonly result = signal<AssistCustomerResult | null>(null);
  readonly errorMessage = signal<string | null>(null);

  readonly form = this.formBuilder.nonNullable.group({
    customerId: ['customer-1', Validators.required],
    orderId: ['order-18273', Validators.required],
    message: [
      'My order is delayed. Check it and open a ticket if necessary.',
      Validators.required
    ]
  });

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.result.set(null);
    this.errorMessage.set(null);

    this.supportApi
      .assist(this.form.getRawValue())
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => this.loading.set(false))
      )
      .subscribe({
        next: result => this.result.set(result),
        error: error => this.handleError(error)
      });
  }

  private handleError(error: HttpErrorResponse): void {
    if (error.status === 404) {
      this.errorMessage.set('Order not found.');
      return;
    }

    this.errorMessage.set(
      'Unable to contact the support API.'
    );
  }
}
