package deal.mart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 10/13/2017.
 */

public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ValueEventListener {

    private Context context;
    private TextView product,content,date,user;
    private ImageView contact;

    public NotificationHolder(View itemView) {
        super(itemView);
        product = (TextView) itemView.findViewById(R.id.notification_product);
        content = (TextView) itemView.findViewById(R.id.notification_content);
        date = (TextView) itemView.findViewById(R.id.notification_date);
        user = (TextView) itemView.findViewById(R.id.notification_user);
        contact = (ImageView) itemView.findViewById(R.id.notification_contact);

        contact.setOnClickListener(this);

        product.setSelected(true);
        content.setSelected(true);
        date.setSelected(true);
        user.setSelected(true);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setProduct(String product){
        this.product.setText(product);
    }
    public void setContent(String content){
        this.content.setText(content);
    }
    public void setDate(String date){
        this.date.setText(date);
    }
    public void setUser(String user){
        this.user.setText(user);
    }

    @Override
    public void onClick(View v) {
        FirebaseDatabase.getInstance().getReference().child("user").child(user.getText().toString()).child("mobile").addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        final String mobile = dataSnapshot.getValue(String.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("This will contact to seller").setMessage("are you sure to buy product ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mobile.replace("m","")));
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context,"please enable call permission",Toast.LENGTH_LONG).show();
                    return;
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // handle for cancel
            }
        }).setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
