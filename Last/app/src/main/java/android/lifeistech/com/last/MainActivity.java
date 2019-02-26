package android.lifeistech.com.last;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public Realm realm;
    public ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        listview = (ListView)findViewById(R.id.listView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Memo memo = (Memo) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate",memo.updateDate);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setMemoList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufilter, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter3) {
            RealmResults<Memo> results = realm.where(Memo.class).equalTo("check",false).findAll();
            List<Memo> items = realm.copyFromRealm(results);

            MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);
            listview.setAdapter(adapter);

        }else if(id == R.id.filter2){
            RealmResults<Memo> results = realm.where(Memo.class).equalTo("check",true).findAll();
            List<Memo> items = realm.copyFromRealm(results);

            MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);
            listview.setAdapter(adapter);


        }else if(id == R.id.filter1){
            setMemoList();
        }
        return true;
    }

    public void toCreateScene (View view){
        Intent intent = new Intent(this,CreateActivity.class);
        startActivity(intent);

    }

    public void setMemoList(){

        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = realm.copyFromRealm(results);

        MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);
        listview.setAdapter(adapter);

    }


}
