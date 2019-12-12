package me.action.addflutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.action.addflutter.utils.LoanComputer;

public class MyNativeActivity extends AppCompatActivity {
    private static final String TAG = "Drawer";
    private DrawerLayout mDrawerLayout;
    private Button btn;
    private Button computeBtn;
    private View leftDrawer;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        btn = findViewById(R.id.my_btn);
        computeBtn = findViewById(R.id.house_loan_compute_btn);
        leftDrawer = findViewById(R.id.drawer_left);
        mTextView = findViewById(R.id.text_menu);

        btn.setOnClickListener(v -> {
            mDrawerLayout.openDrawer(leftDrawer);
        });
        computeBtn.setOnClickListener(v -> {
            startActivity(new Intent(MyNativeActivity.this, LoanActivity.class));
        });

        mTextView.setOnClickListener(v -> {
            mDrawerLayout.closeDrawer(leftDrawer);
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.d(TAG, "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.d(TAG, "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.d(TAG, "onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.d(TAG, "onDrawerStateChanged");
            }
        });
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
}
