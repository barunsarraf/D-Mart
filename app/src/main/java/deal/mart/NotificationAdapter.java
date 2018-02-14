package deal.mart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Admin on 10/13/2017.
 */

public class NotificationAdapter extends FirebaseRecyclerAdapter {
    private Context context;
    public NotificationAdapter(Context context, Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object model, int position) {
        NotificationHolder holder = (NotificationHolder) viewHolder;
        Notification notification = (Notification) model;
        holder.setContent(notification.content);
        holder.setProduct(notification.product);
        holder.setUser(notification.user);
        holder.setDate(notification.date);
        holder.setContext(context);
    }
}
