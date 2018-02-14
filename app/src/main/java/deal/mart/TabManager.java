package deal.mart;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 10/9/2017.
 */

public class TabManager {

    private Context context;
    private ArrayList<TabAdapter> tabAdapters;
    private String [] query = {"book","cycle","laptop","smartphone","watch","other"};

    public TabManager(Context context) {
        this.context = context;
        tabAdapters = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("sale");
        for(String query : this.query) {
            TabAdapter tabAdapter = new TabAdapter(context,Product.class, R.layout.layout_tab_product, TabHolder.class, ref.child(query));
            tabAdapters.add(tabAdapter);
        }
    }

    public TabAdapter getAdapter(int position){
        return tabAdapters.get(position);
    }

    public ArrayList<TabAdapter> getAllAdapter(){
        return tabAdapters;
    }
    public Context getContext(){
        return context;
    }



}
