package nepheus.capacitor.safearea;

import android.content.res.Resources;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "SafeArea")
public class SafeAreaPlugin extends Plugin {
    @PluginMethod
    public void getSafeArea(PluginCall call) {
        WindowInsets windowInsets = this.getBridge().getActivity().getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            call.reject("WindowInsets not available");
            return;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            call.reject("DisplayCutout not available");
            return;
        }

        JSObject ret = new JSObject();
        ret.put("top", this.convertDimension(displayCutout.getSafeInsetTop()));
        ret.put("left", this.convertDimension(displayCutout.getSafeInsetLeft()));
        ret.put("bottom", this.convertDimension(displayCutout.getSafeInsetBottom()));
        ret.put("right", this.convertDimension(displayCutout.getSafeInsetRight()));
        call.resolve(ret);
    }

    @PluginMethod
    public void getStatusBarHeight(PluginCall call) {
        Resources resources = getActivity().getResources();

        float dimension = this.convertDimension(
                resources.getDimension(
                        resources.getIdentifier("status_bar_height", "dimen", "android")
                ));

        JSObject ret = new JSObject();
        ret.put("value", dimension);
        call.resolve(ret);
    }

    @PluginMethod
    public void getNavigationBarHeight(PluginCall call) {
        Resources resources = getActivity().getResources();

        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");

        float dimension = 0;
        if (id > 0 && resources.getBoolean(id)) {
            dimension = this.convertDimension(
                    resources.getDimension(
                            resources.getIdentifier("navigation_bar_height", "dimen", "android")
                    ));
        }

        JSObject ret = new JSObject();
        ret.put("value", dimension);
        call.resolve(ret);
    }

    private int convertDimension(float dimension) {
        return (int) Math.ceil(dimension / this.getContext().getResources().getDisplayMetrics().density);
    }
}
