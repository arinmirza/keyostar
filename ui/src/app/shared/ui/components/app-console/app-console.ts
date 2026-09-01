import { Component, inject, output, signal } from '@angular/core';
import { KeyostarResponse } from '../../../../app';
import { ZardCardImports } from '@/shared/zard-ui/components/card/card.imports';
import { ZardButtonComponent } from '@/shared/zard-ui/components/button';
import { Keyostar } from '@/keyostar';
import { ZardInputGroupImports } from '@/shared/zard-ui/components/input-group';
import { ZardInputComponent } from '@/shared/zard-ui/components/input';
import { ZardFieldImports } from '@/shared/zard-ui/components/field';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-console',
  imports: [
    ZardCardImports, 
    ZardButtonComponent,
    ZardInputComponent,
    ZardInputGroupImports,
    ZardFieldImports,
  ],
  templateUrl: './app-console.html',
})
export class AppConsoleComponent {
  private readonly keyostar = inject(Keyostar);

  readonly responseReceived = output<KeyostarResponse>();

  readonly currentKey = signal<string>('');
  readonly currentValue = signal<string>('');

  get(key: string) {
    console.debug('Clicked on GET with key=', key);
    const startedAt = performance.now();
    this.keyostar.get(key).subscribe({
      next: (response) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'GET',
          key,
          body: response.body ?? '',
          status: response.status,
          durationMs,
        });
      },
      error: (errorResponse: HttpErrorResponse) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'GET',
          key,
          body: JSON.stringify(errorResponse.error),
          status: errorResponse?.status,
          durationMs,
        });
      },
    });
  }

  put(key: string, value: string) {
    console.debug(`Clicked on PUT with key=${key} and value=${value}`);
    const startedAt = performance.now();
    this.keyostar.put(key, value).subscribe({
      next: (response) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'PUT',
          key,
          body: response.body ?? '',
          status: response.status,
          durationMs: durationMs,
        });
      },
      error: (errorResponse: HttpErrorResponse) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'PUT',
          key,
          body: JSON.stringify(errorResponse.error),
          status: errorResponse?.status,
          durationMs,
        });
      },
    });
  }

  remove(key: string) {
    console.debug('Clicked on DELETE with key=', key);
    const startedAt = performance.now();
    this.keyostar.delete(key).subscribe({
      next: (response) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'DELETE',
          key,
          body: response.body ?? '',
          status: response.status,
          durationMs: durationMs,
        });
      },
      error: (errorResponse: HttpErrorResponse) => {
        const durationMs = performance.now() - startedAt;
        this.responseReceived.emit({
          method: 'DELETE',
          key,
          body: JSON.stringify(errorResponse.error),
          status: errorResponse?.status,
          durationMs: durationMs,
        });
      },
    });
  }
}
