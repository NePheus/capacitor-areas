import { registerPlugin } from '@capacitor/core';

import type { AreasPlugin } from './definitions';

const Areas = registerPlugin<AreasPlugin>('Areas', {
  web: () => import('./web').then(m => new m.AreasWeb()),
});

export * from './definitions';
export { Areas };
