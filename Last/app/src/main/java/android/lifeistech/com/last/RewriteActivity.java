package android.lifeistech.com.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;

public class RewriteActivity extends AppCompatActivity {

    public Realm realm;
    public EditText titleText;
    public EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewrite);

        titleText = (EditText)findViewById(R.id.titleEditText);
        contentText = (EditText)findViewById(R.id.contentEditText);
        realm = Realm.getDefaultInstance();

        showDeta();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    public void showDeta(){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();
        titleText.setText(memo.title);
        contentText.setText(memo.content);
    }

    public void update(View v){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.title = titleText.getText().toString();
                memo.content = contentText.getText().toString();
            }
        });
        finish();
    }
}

