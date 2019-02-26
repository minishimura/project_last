package android.lifeistech.com.last;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity {

    public Realm realm;
    public TextView titleText;
    public TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleText = (TextView) findViewById(R.id.titleShowText);
        contentText = (TextView) findViewById(R.id.contentShowText);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onResume(){
        super.onResume();
        showDeta1();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_delete) {
            delete();
        }
        return true;
    }

    public void showDeta1(){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();
        titleText.setText(memo.title);
        contentText.setText(memo.content);
    }

    public void toUpdateScene(View v) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();
        Intent intent = new Intent(this,RewriteActivity.class);
        intent.putExtra("updateDate",memo.updateDate);
        startActivity(intent);
    }

    public void delete(){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.deleteFromRealm();
            }
        });
        finish();
    }
}