package deal.mart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Admin on 10/9/2017.
 */

public class TabAdapter extends FirebaseRecyclerAdapter {
    private Context context;

    public TabAdapter(Context context, Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object model, int position) {
        TabHolder holder = (TabHolder) viewHolder;
        Product product = (Product) model;
        holder.setProduct(product.product);
        holder.setPrice(product.price);
        holder.setDescription(product.description);
        holder.setDate(product.date);
        holder.setRating((float) Double.parseDouble(product.rating.replace("R","")));
        Glide.with(context).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(product.image+".low")).into(holder.getIcon());
    }

}
