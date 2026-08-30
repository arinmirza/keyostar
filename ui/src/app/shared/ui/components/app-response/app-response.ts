import { Component, input } from '@angular/core';
import { KeyostarResponse } from '../../../../app';
import { ZardCardImports } from '@/shared/zard-ui/components/card/card.imports';
import { ZardButtonComponent } from '@/shared/zard-ui/components/button';
import { LucideGhost } from '@lucide/angular'
import { NgIcon } from '@ng-icons/core';

@Component({
  selector: 'app-response',
  templateUrl: './app-response.html',
  imports: [
    ZardCardImports,
    ZardButtonComponent,
    NgIcon,
    LucideGhost
  ]
})
export class AppResponseComponent {
  readonly response = input<KeyostarResponse | null>(null);
}