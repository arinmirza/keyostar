import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Keyostar } from './keyostar';

import { ZardCardImports } from '@/shared/zard-ui/components/card/card.imports';
import { ZardTabsImports } from '@/shared/zard-ui/components/tabs/tabs.imports';
import { ZardButtonComponent } from '@/shared/zard-ui/components/button';
import { ClusterStatusComponent } from './shared/ui/components/cluster-status/cluster-status';
import { AppConsoleComponent } from './shared/ui/components/app-console/app-console';
import { HistoryComponent } from "./shared/ui/components/history/history";
import { AppResponseComponent } from './shared/ui/components/app-response/app-response';


export interface KeyostarResponse {
  method: 'GET' | 'PUT' | 'DELETE' | 'STATS';
  key: string;
  body: string;
  status: number;
  durationMs: number;
}


@Component({
  imports: [
    RouterOutlet,
    ClusterStatusComponent,
    AppConsoleComponent,
    AppResponseComponent,
    ZardCardImports,
    ZardTabsImports,
    ZardButtonComponent,
    HistoryComponent
],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('ui');
  private readonly keyostar = inject(Keyostar);

  response = signal('');

  history = signal<KeyostarResponse[]>([]);

  readonly lastResponse = signal<KeyostarResponse | null>(null);

  handleResponse(response: KeyostarResponse | null) {
    this.lastResponse.set(response);
    response && this.history.update(entries => [response, ...entries]);
  }


}
