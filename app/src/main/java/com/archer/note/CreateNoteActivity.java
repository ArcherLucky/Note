package com.archer.note;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * 创建新的笔记
 */
public class CreateNoteActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextInputLayout titleLayout;
    TextInputLayout contentLayout;

    EditText titleEdit;
    EditText contentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleLayout = (TextInputLayout) findViewById(R.id.til_title);
        titleEdit = (EditText) findViewById(R.id.et_title);
        titleEdit.addTextChangedListener(new MyTextWatcher(titleEdit));

        contentLayout = (TextInputLayout) findViewById(R.id.til_content);
        contentEdit = (EditText) findViewById(R.id.et_content);
        contentEdit.addTextChangedListener(new MyTextWatcher(contentEdit));
    }

    private boolean validateTitle() {
        String title = titleEdit.getText().toString();
        if(TextUtils.isEmpty(title.trim())) {
            titleLayout.setError(getString(R.string.title_error));
            requestFocus(titleEdit);
            return false;
        } else {
            titleLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateContent() {
        String title = contentEdit.getText().toString();
        if(TextUtils.isEmpty(title.trim())) {
            contentLayout.setError(getString(R.string.content_error));
            requestFocus(contentEdit);
            return false;
        } else {
            contentLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_title:
                    validateTitle();
                    break;
                case R.id.et_content:
                    validateContent();
                    break;
            }
        }
    }

}
