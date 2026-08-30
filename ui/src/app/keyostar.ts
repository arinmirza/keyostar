import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';


export type KeyostarMethod = 
  | 'GET'
  | 'PUT'
  | 'DELETE'
  | 'STATS';

export interface KeyostarQueryResult<T> {
  method: KeyostarMethod;
  key?: string;
  status: number;
  body: T | null;
  durationMs: number;
}

@Service()
export class Keyostar {
  private readonly http = inject(HttpClient);

  get(key: string) {
    console.debug(`[keyostar.ts] get() was called with key=${key}`);
    return this.http.get(
      `/api/gateway/key/${encodeURIComponent(key)}`,
      { 
        responseType: 'text',
        observe: 'response',
      }
    );
  }

  put(key: string, value: string) {
    console.debug(`[keyostar.ts] put() was called with key=${key} and value=${value}`);
    return this.http.put(
      `/api/gateway/key/${encodeURIComponent(key)}`,
      value,
      { 
        responseType: 'text',
        observe: 'response',
      }
    );
  }

  delete(key: string) {
    console.debug(`[keyostar.ts] delete() was called with key=${key}`);
    return this.http.delete(
      `/api/gateway/key/${encodeURIComponent(key)}`,
      { 
        responseType: 'text',
        observe: 'response',
      }
    );
  }

  stats() {
    console.debug(`[keyostar.ts] stats() was called`)
    return this.http.get(
      `/api/gateway/stats`,
      { responseType: 'json' }
    );
  }
}
