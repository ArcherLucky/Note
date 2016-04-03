package com.archer.note;

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
import android.view.WindowManager;
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

    /**
     * 是否为新建Note
     */
    boolean isAdd;

    /**
     * 如果是修改Note，这个为NoteList的position
     */
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (null != getIntent().getBundleExtra(Constant.ACTION_CHANGE_NOTE)) {
            note = getIntent().getBundleExtra(Constant.ACTION_CHANGE_NOTE)
                    .getParcelable(Constant.ACTION_CHANGE_NOTE);
            position = getIntent().getIntExtra("position", -1);
        } else {
            note = new Note();
            isAdd = true;
        }

        titleLayout = (TextInputLayout) findViewById(R.id.til_title);
        titleEdit = (EditText) findViewById(R.id.et_title);
        if(null != titleEdit) {
            titleEdit.setText(note.getTitle());
        }
        titleEdit.addTextChangedListener(new MyTextWatcher(titleEdit));

        contentLayout = (TextInputLayout) findViewById(R.id.til_content);
        contentEdit = (EditText) findViewById(R.id.et_content);
        if(null != contentEdit) {
            contentEdit.setText(note.getTextContent());
        }
        contentEdit.addTextChangedListener(new MyTextWatcher(contentEdit));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_note, menu);
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
                    if(!validateTitle() || !validateContent()) {
                        return super.onOptionsItemSelected(item);
                    }
                    NoteDB.saveNote(note);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.ACTION_CHANGE_NOTE, note);
                    intent.putExtra(Constant.ACTION_CHANGE_NOTE, bundle);
                    intent.putExtra("isAdd", isAdd);
                    intent.putExtra("position", position);
                    setResult(RESULT_OK, intent);
                    finish();

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
        if (TextUtils.isEmpty(title.trim())) {
            contentLayout.setError(null);
            titleLayout.setError(getString(R.string.title_error));
            requestFocus(titleEdit);
            return false;
        } else {
            titleLayout.setErrorEnabled(false);
            note.setTitle(title);
            return true;
        }
    }

    private boolean validateContent() {
        String content = contentEdit.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            contentLayout.setError(null);
            contentLayout.setError(getString(R.string.content_error));
            requestFocus(contentEdit);
            return false;
        } else {
            contentLayout.setErrorEnabled(false);
            note.setTextContent(content);
            return true;
        }
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
