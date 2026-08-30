import { Component, input } from '@angular/core';
import { KeyostarResponse } from '../../../../app';
import { ZardCardImports } from '@/shared/zard-ui/components/card/card.imports';
import { ZardTableImports } from '@/shared/zard-ui/components/table';
import { ZardButtonComponent } from '@/shared/zard-ui/components/button';
import { LucideGhost } from '@lucide/angular';

@Component({
  selector: 'app-history',
  templateUrl: './history.html',
  imports: [
      ZardCardImports,
      ZardTableImports,
      ZardButtonComponent,
      LucideGhost
    ],
})
export class HistoryComponent {
  readonly history = input.required<KeyostarResponse[]>();
}