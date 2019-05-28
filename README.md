
# Add Flutter module to Existing Android Project

1. Create Flutter Module

2. Config settings.gradle

```
setBinding(new Binding([gradle: this]))                                 // new
evaluate(new File(                                                      // new
        settingsDir.parentFile,                                               // new
        'your_part_flutter/.android/include_flutter.groovy'                          // new
))
```

3. Add Dependencies in app/build.gradle

```
implementation project(':flutter')
```

## Use FlutterView in Activity

```
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
```

## Use FlutterView As Fragment

```
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
```

## Provide NativeBridge for Flutter Module


use flutterview construct FlutterNativeBridge

call FlutterNativeBridge.registerPlugin(DefaultMethodCallHandler)

override ths method:

onMethodCall(MethodCall call, MethodChannel.Result result)

```
private void registerFlutterPlugin() {
        if (flutterView != null) {
            mNativeBridge = new FlutterNativeBridge(flutterView);
            mNativeBridge.registerPlugin(new ToastMethodCallHandler(MyFlutterActivity.this, CHANNEL_TOAST));
            mNativeBridge.registerPlugin(new RouteMethodCallHandler(MyFlutterActivity.this, CHANNEL_ROUTE));
        }
    }
```
