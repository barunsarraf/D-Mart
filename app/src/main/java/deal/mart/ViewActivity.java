package deal.mart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

public class ViewActivity extends AppCompatActivity implements ValueEventListener, View.OnClickListener {

    private ImageView image,buy,rate,dialogImage,dialogOk,dialogCancel;
    private TextView product, price, description, date, seller, mail, mobile, address;
    private Product info;
    private Dialog imageDialog,rateDialog;
    private Integer PRODUCT_CATEGORY;
    private RatingBar ratingBar,dialogRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        PRODUCT_CATEGORY = getIntent().getIntExtra("ROW", 0);
        int column = getIntent().getIntExtra("COLUMN", 0);

        info = (Product) ListManager.getAdapter(PRODUCT_CATEGORY).getItem(column);

        imageDialog = new Dialog(this);
        imageDialog.setContentView(R.layout.dialog_image);
        dialogImage = (ImageView) imageDialog.findViewById(R.id.dialog_image);

        rateDialog = new Dialog(this);
        rateDialog.setTitle("Rate this Product");
        rateDialog.setContentView(R.layout.dialog_rate);
        dialogOk = (ImageView) rateDialog.findViewById(R.id.dialog_rateBar_ok);
        dialogCancel = (ImageView) rateDialog.findViewById(R.id.dialog_rateBar_cancel);
        dialogRatingBar = (RatingBar) rateDialog.findViewById(R.id.dialog_rateBar);
        dialogOk.setOnClickListener(this);
        dialogCancel.setOnClickListener(this);

        image = (ImageView) findViewById(R.id.view_image);

        image.setOnClickListener(this);

        rate = (ImageView) findViewById(R.id.view_rate_this);
        rate.setOnClickListener(this);

        buy = (ImageView) findViewById(R.id.view_buy_now);
        buy.setOnClickListener(this);


        product = (TextView) findViewById(R.id.view_product);
        product.setSelected(true);

        price = (TextView) findViewById(R.id.view_price);
        price.setSelected(true);

        description = (TextView) findViewById(R.id.view_description);
        description.setSelected(true);

        date = (TextView) findViewById(R.id.view_date);
        date.setSelected(true);

        ratingBar = (RatingBar) findViewById(R.id.view_rating);

        seller = (TextView) findViewById(R.id.view_seller);
        seller.setSelected(true);

        mail = (TextView) findViewById(R.id.view_mail);
        mail.setSelected(true);

        mobile = (TextView) findViewById(R.id.view_mobile);
        mobile.setSelected(true);

        address = (TextView) findViewById(R.id.view_address);
        address.setSelected(true);

        Glide.with(this).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(info.image+".low")).into(image);

        FirebaseDatabase.getInstance().getReference().child("user").child(info.seller).addListenerForSingleValueEvent(this);

        product.setText(info.product);
        price.setText(info.price);
        description.setText(info.description);
        date.setText(info.date);

        ratingBar.setRating((float) Double.parseDouble(info.rating.replace("R","")));

        imageDialog.setTitle(info.product);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (!dataSnapshot.exists())
            return;
        User user = dataSnapshot.getValue(User.class);
        mobile.setText(user.mobile.replace("m",""));
        mail.setText(user.mail);
        address.setText(user.address);
        seller.setText(user.name);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_image:
                showImageDialog();
                break;
            case R.id.view_rate_this:
                showRateDialog();
                break;
            case R.id.dialog_rateBar_ok:
                applyRate();
                rateDialog.dismiss();
                break;
            case R.id.dialog_rateBar_cancel:
                rateDialog.dismiss();
                break;
            case R.id.view_buy_now:
                sendNotification();
                break;
            default:
        }
    }

    private void showImageDialog() {
        //Glide.with(this).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(info.image+".low")).into(dialogImage);
        Glide.with(this).using(new FirebaseImageLoader()).load(FirebaseStorage.getInstance().getReference().child("image").child(info.image+".high")).into(dialogImage);
        imageDialog.show();
    }

    private void showRateDialog(){
        rateDialog.show();
    }

    private void applyRate(){
        Float rating = ((float)Double.parseDouble(info.rating.replace("R","")) + dialogRatingBar.getRating())/2;
        ratingBar.setRating(rating);
        FirebaseDatabase.getInstance().getReference().child("sale").child(ListManager.productList[PRODUCT_CATEGORY]).child(info.id).child("rating").setValue("R"+rating);
    }

    private void sendNotification(){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Send notification to seller ...");
        dialog.setContentView(R.layout.dialog_notification);
        final EditText notificationText = (EditText) dialog.findViewById(R.id.notification_text);
        Button send = (Button) dialog.findViewById(R.id.notification_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String date = calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
                Notification notification = new Notification(info.product,notificationText.getText().toString().trim(),SignInActivity.getUserName(),date);
                FirebaseDatabase.getInstance().getReference().child("notification").child(info.seller).push().setValue(notification);
                dialog.dismiss();
            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.notification_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
