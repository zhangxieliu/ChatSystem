package com.fosu.chatsystem.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fosu.chatsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.favorites) {
                    // onclick

                    return true;
                }
                return false;
            }
        });

        // 编程方式设置选中item
//        mBottomNavigationView.getMenu().getItem(需要选中的item位置).setChecked(true);
    }

    /**
     * 得到选中的item
     * @return
     */
    private int getSelectedItem() {
        Menu menu = mBottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }
}
