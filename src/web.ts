import { WebPlugin } from '@capacitor/core';

import type { Areas, AreasPlugin } from './definitions';

export class AreasWeb extends WebPlugin implements AreasPlugin {
  constructor() {
    super({
      name: 'Areas',
      platforms: ['web'],
    });
  }

  async getSafeArea(): Promise<Areas> {
    throw new Error('Method not implemented.');
  }

  async getStatusBarHeight(): Promise<{
    value: number;
  }> {
    throw new Error('Method not implemented.');
  }

  async getNavigationBarHeight(): Promise<{
    value: number;
  }> {
    throw new Error('Method not implemented.');
  }
}
