export interface Areas {
  top: number;
  left: number;
  bottom: number;
  right: number;
}

export interface AreasPlugin {
  /**
   * Get the safe area of the device
   */
  getSafeArea(): Promise<Areas | undefined>;

  /**
   * Get the status bar height of the device
   */
  getStatusBarHeight(): Promise<{
    value: number;
  }>;

  /**
   * Get the navigation bar height of the device. Returns 0 when the device has hardware buttons.
   */
  getNavigationBarHeight(): Promise<{
    value: number;
  }>;
}
