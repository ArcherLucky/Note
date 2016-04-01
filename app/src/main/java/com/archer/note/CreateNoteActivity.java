package com.archer.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.archer.note.constant.Constant;
import com.archer.note.db.Note;
import com.archer.note.db.NoteDB;

/**
 * 创建新的笔记
 */
public class CreateNoteActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextInputLayout titleLayout;
    TextInputLayout contentLayout;

    EditText titleEdit;
    EditText contentEdit;

    Note note;

    InputMethodManager imm;

    /**
     *是否为新建Note
     */
    boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Window window = getWindow();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(null != getIntent().getBundleExtra(Constant.ACTION_CHANGE_NOTE)) {
            note = getIntent().getBundleExtra(Constant.ACTION_CHANGE_NOTE)
                    .getParcelable(Constant.ACTION_CHANGE_NOTE);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            note = new Note();
            isAdd = true;
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        titleLayout = (TextInputLayout) findViewById(R.id.til_title);
        titleEdit = (EditText) findViewById(R.id.et_title);
        titleEdit.setText(note.getTitle());
        titleEdit.addTextChangedListener(new MyTextWatcher(titleEdit));

        contentLayout = (TextInputLayout) findViewById(R.id.til_content);
        contentEdit = (EditText) findViewById(R.id.et_content);
        contentEdit.setText(note.getTextContent());
        contentEdit.addTextChangedListener(new MyTextWatcher(contentEdit));
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(!isAdd) {
            titleEdit.setEnabled(false);
            contentEdit.setEnabled(false);
            imm.hideSoftInputFromWindow(titleEdit.getWindowToken(), 0); //强制隐藏键盘
        } else {
            imm.showSoftInput(titleEdit, InputMethodManager.SHOW_FORCED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_note, menu);
        if(!isAdd) {
            MenuItem menuItem = menu.getItem(0);
            menuItem.setTitle(R.string.action_edit);
            menuItem.setIcon(R.drawable.ic_mode_edit_white_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_save:
                if(item.getTitle().equals(getString(R.string.action_save))) {
                    NoteDB.saveNote(note);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.ACTION_CHANGE_NOTE, note);
                    intent.putExtra(Constant.ACTION_CHANGE_NOTE, bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    item.setIcon(R.drawable.ab_save);
                    item.setTitle(R.string.action_save);
                    titleEdit.setEnabled(true);
                    contentEdit.setEnabled(true);
                    requestFocus(titleEdit);
                }

//                View view = toolbar.findViewById(R.id.action_save);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//                animator.setDuration(200);
//                animator.start();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        note.setTitle(title);
        return true;
    }

    private boolean validateContent() {
        String content = contentEdit.getText().toString();
        if(TextUtils.isEmpty(content.trim())) {
            contentLayout.setError(getString(R.string.content_error));
            requestFocus(contentEdit);
            return false;
        } else {
            contentLayout.setErrorEnabled(false);
        }
        note.setTextContent(content);
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
