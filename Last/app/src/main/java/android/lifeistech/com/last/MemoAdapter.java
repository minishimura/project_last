package android.lifeistech.com.last;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class MemoAdapter extends ArrayAdapter<Memo> {

    public LayoutInflater layoutinflater;
    public List<Memo> memos;



    MemoAdapter(Context context, int textViewResourceId, List<Memo> objects){
        super(context,textViewResourceId,objects);
        layoutinflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memos = objects;


    }

    public static class ViewHolder{

        TextView titleText;
        CheckBox checkbox;

        ViewHolder(View view){
            titleText = (TextView)view.findViewById(R.id.titleText);
            checkbox = (CheckBox)view.findViewById(R.id.checkbox);
        }


    }

    @Override
    public Memo getItem(int position) {
        return memos.get(position);
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent){

        Log.d("getView", String.valueOf(position));

        final ViewHolder viewHolder;
        final Memo memotemp = getItem(position);

        if(convertView==null){
            convertView = layoutinflater.inflate(R.layout.layout_item_memo,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        TextView titleText = (TextView)convertView.findViewById(R.id.titleText);
//        final CheckBox ch = (CheckBox)convertView.findViewById(R.id.checkbox);
//        TextView contentText = (TextView)convertView.findViewById(R.id.contentText);
        viewHolder.titleText.setText(memotemp.title);
        viewHolder.checkbox.setChecked(memotemp.check);

        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {

            Realm realm = Realm.getDefaultInstance();
            final Memo memo = realm.where(Memo.class).equalTo("updateDate",memotemp.updateDate).findFirst();

            @Override
            public void onClick(View v) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if(viewHolder.checkbox.isChecked()==true){
                            memo.check = true;
                        }else{
                            memo.check = false;
                        }
                    }
                });realm.close();
            }

        });
        return convertView;

    }
}
