package nepheus.capacitor.areas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Areas")
public class AreasPlugin extends Plugin {
    @PluginMethod
    public void getSafeArea(PluginCall call) {
        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            WindowInsets windowInsets =
                    this.getBridge().getActivity().getWindow().getDecorView().getRootWindowInsets();
            if (windowInsets == null) {
                call.reject("WindowInsets not available");
                return;
            }

            DisplayCutout displayCutout = windowInsets.getDisplayCutout();
            if (displayCutout == null) {
                call.reject("DisplayCutout not available");
                return;
            }

            top = this._convertToDeviceHeight(displayCutout.getSafeInsetTop());
            left = this._convertToDeviceHeight(displayCutout.getSafeInsetLeft());
            bottom = this._convertToDeviceHeight(displayCutout.getSafeInsetBottom());
            right = this._convertToDeviceHeight(displayCutout.getSafeInsetRight());
        }

        JSObject ret = new JSObject();
        ret.put("top", top);
        ret.put("left", left);
        ret.put("bottom", bottom);
        ret.put("right", right);
        call.resolve(ret);
    }

    @PluginMethod
    public void getStatusBarHeight(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", this._getStatusBarHeight());
        call.resolve(ret);
    }

    @PluginMethod
    public void getNavigationBarHeight(PluginCall call) {
        WindowManager windowManager =
                (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = 0;
        if (Build.VERSION.SDK_INT >= 30) {
            height = this._convertToDeviceHeight(windowManager
                    .getCurrentWindowMetrics()
                    .getWindowInsets()
                    .getInsets(WindowInsets.Type.navigationBars())
                    .bottom);
        } else {
            Display display = windowManager.getDefaultDisplay();
            Point appUsableSize = new Point();
            display.getSize(appUsableSize);
            Point realScreenSize = new Point();
            display.getRealSize(realScreenSize);

            if (appUsableSize.y < realScreenSize.y) {
                // navigation bar at the bottom
                height = this._convertToDeviceHeight(realScreenSize.y - appUsableSize.y);
            } else if (appUsableSize.x < realScreenSize.x) {
                // navigation bar on the side
                height = this._convertToDeviceHeight(appUsableSize.y);
            }

            height -= _getStatusBarHeight();
        }

        JSObject ret = new JSObject();
        ret.put("value", height);
        call.resolve(ret);
    }

    private int _getStatusBarHeight() {
        Resources resources = getActivity().getResources();
        return this._convertToDeviceHeight(
                resources.getDimension(
                        resources.getIdentifier("status_bar_height", "dimen", "android")
                ));
    }

    private int _convertToDeviceHeight(float height) {
        return (int) Math.ceil(height / this.getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}
