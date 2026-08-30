import { Component, inject, signal } from '@angular/core';
import { ZardCardImports } from '@/shared/zard-ui/components/card/card.imports';
import { ZardTableImports } from '@/shared/zard-ui/components/table/table.imports';
import { ZardButtonComponent } from '@/shared/zard-ui/components/button';
import { LucideGhost } from '@lucide/angular';
import { Keyostar } from '@/keyostar';

interface StoreStatus {
  id: number;
  available: boolean;
  size: number;
}

@Component({
  selector: 'app-cluster-status',
  imports: [
    ZardCardImports,
    ZardTableImports,
    ZardButtonComponent,
    LucideGhost
  ],
  templateUrl: './cluster-status.html',
  //styleUrl: './cluster-status.css'
})
export class ClusterStatusComponent {
  private readonly keyostar = inject(Keyostar);

  readonly stores = signal<StoreStatus[]>([]);

  stats() {
    this.keyostar.stats().subscribe({
      next: (value) => {
        if(Array.isArray(value)) {
          this.stores.set(value);
        }
      },
      error: (err) => {
        console.error("Oh no, this should not have happened.")
        console.error(err);
      } 
    });
  }
}