package me.action.addflutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> openFlutterActivity("user_list", MyFlutterActivity.STYLE_FULLSREEN));
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(v -> openFlutterActivity("product_list", MyFlutterActivity.STYLE_FULLSREEN));
        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(v -> openFlutterActivity("demo", MyFlutterActivity.STYLE_NO_TITLE));
    }


    private void openFlutterActivity(String route) {
        openFlutterActivity(route, 0);
    }

    private void openFlutterActivity(String route, int style) {
        Intent intent = new Intent(MainActivity.this, MyFlutterActivity.class);
        intent.putExtra(MyFlutterActivity.EXTRA_ROUTE_NAME, route);
        intent.putExtra(MyFlutterActivity.EXTRA_NO_TITLEBAR, style >= MyFlutterActivity.STYLE_NO_TITLE);
        intent.putExtra(MyFlutterActivity.EXTRA_IS_FULLSREEN, style >= MyFlutterActivity.STYLE_FULLSREEN);
        startActivity(intent);
    }
}
