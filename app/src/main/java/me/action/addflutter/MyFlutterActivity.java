package me.action.addflutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import io.flutter.facade.Flutter;
import io.flutter.facade.FlutterFragment;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;
import me.action.addflutter.demo.RouteMethodCallHandler;
import me.action.addflutter.demo.ToastMethodCallHandler;
import me.action.addflutter.plugin.FlutterNativeBridge;
import me.action.addflutter.utils.StatusBarUtil;

public class MyFlutterActivity extends AppCompatActivity implements MethodChannel.MethodCallHandler {
    private static final String CHANNEL_TOAST = "me.action.plugins/toast";
    private static final String CHANNEL_ROUTE = "me.action.plugins/route";

    private FrameLayout mFlutterContainer;
    private FlutterView flutterView;
    private FlutterFragment mFlutterFragment;

    private FlutterNativeBridge mNativeBridge;

    public static final String EXTRA_ROUTE_NAME = "route_name";
    public static final String EXTRA_IS_FULLSREEN = "is_fullsreen";
    public static final String EXTRA_NO_TITLEBAR = "no_titlebar";

    public static final int STYLE_NO_TITLE = 1;
    public static final int STYLE_FULLSREEN = 2;

    private boolean noTitleBar;
    private boolean isFullSreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 定制状态栏样式
        customizeStatusBarStyle();

        setContentView(R.layout.activity_flutter);

        mFlutterContainer = findViewById(R.id.flutter_container);
        String route = getIntent().getStringExtra(EXTRA_ROUTE_NAME);
        if (BuildConfig.DEBUG) {
            findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);
        }
        initFlutterView(route);
//        initFlutterFragment();
        registerFlutterPlugin();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void customizeStatusBarStyle() {
        noTitleBar = getIntent().getBooleanExtra(EXTRA_NO_TITLEBAR, false);
        isFullSreen = getIntent().getBooleanExtra(EXTRA_IS_FULLSREEN, false);
        if (noTitleBar || isFullSreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } else {
            requestWindowFeature(Window.FEATURE_ACTION_BAR);
        }
        if (isFullSreen) {
            StatusBarUtil.translucentStatus(getWindow());
            StatusBarUtil.changeStatusBarTextColor(getWindow(), true);
        }
    }

    private void registerFlutterPlugin() {
        if (flutterView != null) {
            mNativeBridge = new FlutterNativeBridge(flutterView);
            mNativeBridge.registerPlugin(new ToastMethodCallHandler(MyFlutterActivity.this, CHANNEL_TOAST));
            mNativeBridge.registerPlugin(new RouteMethodCallHandler(MyFlutterActivity.this, CHANNEL_ROUTE));
        }
    }

    private void initFlutterView(String route) {
        flutterView = Flutter.createView(this, getLifecycle(), route);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mFlutterContainer.addView(flutterView, layout);

        if (BuildConfig.DEBUG) {    // debug模式下，有黑屏过程，用loading view遮盖，release模式下不存在该问题
            FlutterView.FirstFrameListener[] listeners = new FlutterView.FirstFrameListener[1];
            listeners[0] = () -> {
                findViewById(R.id.loading_layout).setVisibility(View.GONE);
            };
            flutterView.addFirstFrameListener(listeners[0]);
        }
    }

    private void initFlutterFragment(String route) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        mFlutterFragment = Flutter.createFragment(route);
        tx.replace(R.id.flutter_container, mFlutterFragment);
        tx.commit();

        mFlutterFragment.getFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                flutterView = (FlutterView) v;
                registerFlutterPlugin();
            }
        }, false);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals("showToast")) {
            Toast.makeText(MyFlutterActivity.this, call.argument("msg"), Toast.LENGTH_LONG).show();
        } else if (call.method.equals("navigatorTo")) {
            Intent intent = new Intent(MyFlutterActivity.this, MyNativeActivity.class);
            startActivity(intent);
        }
    }
}
