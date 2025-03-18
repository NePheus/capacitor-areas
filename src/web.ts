import { WebPlugin } from '@capacitor/core';

import type { Areas, AreasPlugin } from './definitions';

export class AreasWeb extends WebPlugin implements AreasPlugin {
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
