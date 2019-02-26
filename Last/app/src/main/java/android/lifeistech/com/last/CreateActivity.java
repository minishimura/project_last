package android.lifeistech.com.last;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class CreateActivity extends AppCompatActivity {

    public EditText titleEditText;
    public EditText contentEditText;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);
        realm = Realm.getDefaultInstance();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    public void memoCreate(View v) {
        String title = titleEditText.getText().toString();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE);
        String updateDate = sdf.format(date);
        String content = contentEditText.getText().toString();

        save(title, updateDate, content);
        finish();

    }

    public void save(final String title, final String updateDate, final String content) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Memo memo = realm.createObject(Memo.class);
                memo.title = title;
                memo.updateDate = updateDate;
                memo.content = content;
                memo.check = false;
            }
        });
    }


}