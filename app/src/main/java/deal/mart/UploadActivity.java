package deal.mart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, OnSuccessListener<UploadTask.TaskSnapshot>, OnCompleteListener<UploadTask.TaskSnapshot>, OnFailureListener, AdapterView.OnItemSelectedListener {

    private String [] cat = {"book" ,"cycle" , "laptop" , "smartphone" ,"watch" ,"other"};

    private ProgressDialog dialog;
    private ImageView preview;
    private EditText product,price,description;
    private ImageView upload,gallery,camera;
    private Spinner spinner;
    private String category;
    private byte [] lowBytes,highBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Uploading");
        dialog.setMessage("Processing ...");

        preview = (ImageView) findViewById(R.id.upload_preview);
        product = (EditText) findViewById(R.id.upload_product);
        price = (EditText) findViewById(R.id.upload_price);
        description = (EditText) findViewById(R.id.upload_description);

        upload = (ImageView) findViewById(R.id.upload_button);
        gallery = (ImageView) findViewById(R.id.gallery_button);
        camera = (ImageView) findViewById(R.id.camera_button);

        spinner = (Spinner) findViewById(R.id.spinner);

        upload.setOnClickListener(this);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cat));

    }

    private void upload(){
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gallery_button:
                openGallery();
                break;
            case R.id.upload_button:
                uploadProduct();
                break;
            case R.id.camera_button:
                openCamera();
                break;
            default:
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }

    private void uploadProduct() {

        if(lowBytes==null ){
            Toast.makeText(this,"no file selected",Toast.LENGTH_LONG).show();
            return;
        }
        Calendar calendar = Calendar.getInstance();

        String _id = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String _product = product.getText().toString().trim();
        String _price = "Rs : "+price.getText().toString().trim();
        String _description = description.getText().toString().trim();
        String _date = calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        String _rating = "R0";
        String _image = _product + "@" + SignInActivity.getUserName();
        String _seller = SignInActivity.getUserName();

        if(_product.length()==0 || _price.length()==0 || _description.length()==0){
            Toast.makeText(this,"improper details",Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();
        FirebaseStorage.getInstance().getReference().child("image").child(_image+".low").putBytes(lowBytes);
        FirebaseStorage.getInstance().getReference().child("image").child(_image+".high").putBytes(highBytes).addOnSuccessListener(this).addOnCompleteListener(this).addOnFailureListener(this);
        FirebaseDatabase.getInstance().getReference().child("sale").child(category).child(_id).setValue(new Product(_id,_product,_price,_image,_description,_date,_rating,_seller));
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK)
            return;
        switch (requestCode){
            case 1:
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    preview.setImageBitmap(bitmap);
                    lowBytes = lowBitmap(bitmap);
                    highBytes = highBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                preview.setImageBitmap(bitmap);
                lowBytes = lowBitmap(bitmap);
                highBytes = highBitmap(bitmap);
                break;
            default:
        }

    }

    private byte [] lowBitmap(Bitmap bitmap){
        float scale = 256.0f/bitmap.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth()*scale),256,false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private byte [] highBitmap(Bitmap bitmap){
        float scale = 256.0f/bitmap.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        dialog.dismiss();
        Toast.makeText(this,"successfully uploaded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
        dialog.dismiss();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        dialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = cat[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
