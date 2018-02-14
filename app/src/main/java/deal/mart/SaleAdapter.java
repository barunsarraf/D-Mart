package deal.mart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * Created by Admin on 10/11/2017.
 */

public class SaleAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Product> productArrayList;
    private ArrayList<Integer> categoryList;

    public SaleAdapter(Context context, ArrayList<Product> productArrayList,ArrayList<Integer> categoryList) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.categoryList = categoryList;
    }

    public Context getContext() {
        return context;
    }

    public Product getProduct(int position) {
        return productArrayList.get(position);
    }

    public Integer getCategory(int position){
        return categoryList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sale_row,parent,false);
        SaleHolder holder = new SaleHolder(this,view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SaleHolder holder = (SaleHolder) viewHolder;
        Product product = productArrayList.get(position);
        holder.setProduct(product.product);
        Glide.with(context).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(product.image+".low")).into(holder.getIcon());
    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public void removeItem(int position) {
        productArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
