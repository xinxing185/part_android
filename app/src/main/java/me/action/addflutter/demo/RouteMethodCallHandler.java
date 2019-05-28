package me.action.addflutter.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import me.action.addflutter.MyNativeActivity;
import me.action.addflutter.plugin.DefaultMethodCallHandler;

/**
 * RouteMethodCallHandler
 * Created by zxx on 2019-05-14 18:28
 */
public class RouteMethodCallHandler extends DefaultMethodCallHandler {
    public RouteMethodCallHandler(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        super.onMethodCall(call, result);
        if (call.method.equals("navigatorTo")) {
            Intent intent = new Intent(mContext, MyNativeActivity.class);
            mContext.startActivity(intent);
        } else if (call.method.equals("finish")) {
            ((Activity) mContext).finish();
        }
    }
}
