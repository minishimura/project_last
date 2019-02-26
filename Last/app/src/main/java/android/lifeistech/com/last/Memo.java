package android.lifeistech.com.last;

import io.realm.RealmObject;

public class Memo extends RealmObject {
    public String title;
    public String content;
    public String updateDate;
    public boolean check;
}
