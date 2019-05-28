package me.action.addflutter.plugin;

import android.content.Context;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * DefaultMethodCallHandler
 * Created by zxx on 2019-05-14 17:53
 */
public class DefaultMethodCallHandler implements MethodChannel.MethodCallHandler {
    protected Context mContext;
    protected String mName;

    public DefaultMethodCallHandler(Context context, String name) {
        mContext = context;
        mName = name;
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
    }

    public String getName() {
        return mName;
    }
}
