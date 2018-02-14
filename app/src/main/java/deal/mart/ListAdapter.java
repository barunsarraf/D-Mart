package deal.mart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Admin on 9/30/2017.
 */

public class ListAdapter extends FirebaseRecyclerAdapter {

    private Context context;
    private int INDEX;

    public ListAdapter(Context context, Integer index , Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.INDEX = index;
    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object model, int position) {
        ListHolder holder = (ListHolder) viewHolder;
        Product product = (Product) model;
        holder.setProduct(product.product);
        holder.setPrice(product.price);
        holder.setROW(INDEX);
        holder.setContext(context);
        Glide.with(context).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(product.image+".low")).into(holder.getImage());
    }

}

