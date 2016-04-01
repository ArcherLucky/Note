package com.archer.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity基类
 * Created by 84113 on 2016/4/1.
 */
public class BaseActivity extends AppCompatActivity {

    protected Intent getNoteIntent(Class<?> cls, String action) {
        Intent intent = new Intent(this, cls);
        intent.setAction(action);
        return intent;
    }

}
