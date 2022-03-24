import { WebPlugin } from '@capacitor/core';

import type { SafeArea, SafeAreaPlugin } from './definitions';

export class SafeAreaWeb extends WebPlugin implements SafeAreaPlugin {
  constructor() {
    super({
      name: 'SafeArea',
      platforms: ['web'],
    });
  }

  async getSafeArea(): Promise<SafeArea> {
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
