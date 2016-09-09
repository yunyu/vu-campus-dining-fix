package es.nkmem.da.vucampusdining;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHook implements IXposedHookLoadPackage {
    public static String TAG = "[Campus Dining Patch] ";
    public static final String PACKAGE_VUDINING = "com.thecampusdiningapp.vanderbilt";
    private static final String CLASS_SPECIFICFUNCTIONS = "com.thecampusdiningapp.vanderbilt.SpecificFunctions";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(PACKAGE_VUDINING)) {
            return;
        }
        XposedBridge.log(TAG + "Replacing decodeJSON_others");
        Class<?> classSpecificFunctions = XposedHelpers.findClass(CLASS_SPECIFICFUNCTIONS, lpparam.classLoader);
        XposedHelpers.findAndHookMethod(classSpecificFunctions, "decodeJSON_others", String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // XposedBridge.log(TAG + "decodeJSON_others: " + param.args[0] + ", " + param.args[1]);
                if (param.args[1].equals("pastmidnight")) {
                    XposedBridge.log(TAG + "Hijacking pastmidnight");
                    final JSONArray jsonArray = new JSONObject((String) param.args[0]).getJSONArray((String) param.args[1]);
                    param.setResult(new String[jsonArray.length()]);
                }
            }
        });
    }
}
