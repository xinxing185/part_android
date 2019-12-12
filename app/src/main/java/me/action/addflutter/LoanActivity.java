package me.action.addflutter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.action.addflutter.utils.LoanComputer;

/**
 * LoanActivity
 * Created by zxx on 2019-12-11 18:08
 */
public class LoanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_compute);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        LoanComputer loanComputer = new LoanComputer();
//        loanComputer.loanMethod1(185, 360, 4.41f);
    }

}
