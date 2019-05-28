package me.action.addflutter.demo;

import android.content.Context;
import android.widget.Toast;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import me.action.addflutter.plugin.DefaultMethodCallHandler;

/**
 * ToastMethodCallHandler
 * Created by zxx on 2019-05-14 18:26
 */
public class ToastMethodCallHandler extends DefaultMethodCallHandler {
    public ToastMethodCallHandler(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        super.onMethodCall(call, result);
        if (call.method.equals("showToast")) {
            Toast.makeText(mContext, call.argument("msg"), Toast.LENGTH_LONG).show();
        }
    }
}
