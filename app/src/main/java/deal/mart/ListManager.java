package deal.mart;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 9/30/2017.
 */

public class ListManager {

    public static int BOOK = 0 ,CYCLE = 1,LAPTOP = 2,SMART_PHONE = 3,WATCH = 4,OTHER = 5;


    private static Context context;
    private static ArrayList<ListAdapter> listAdapterList;
    public static String [] productList,statusList;

    public static void initAdapter(Context context_){
        context = context_;
        listAdapterList = new ArrayList<>();
        productList = context.getResources().getStringArray(R.array.product);
        statusList = context.getResources().getStringArray(R.array.status);
        createAdapterList();
    }

    private static void createAdapterList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("/sale");

        for(int i=0;i<productList.length;i++){
            ListAdapter listAdapter = new ListAdapter(context,i,Product.class,R.layout.layout_product,ListHolder.class,ref.child("/"+productList[i]));
            listAdapterList.add(listAdapter);
        }
    }

    public static ListAdapter getAdapter(int INDEX){
        return listAdapterList.get(INDEX);
    }

    public static ArrayList<ListAdapter> getAllAdapter() {
        return listAdapterList;
    }
}
