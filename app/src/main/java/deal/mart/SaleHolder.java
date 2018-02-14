package deal.mart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Admin on 10/11/2017.
 */

public class SaleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SaleAdapter saleAdapter;
    private ImageView saleIcon,saleDelete;
    private TextView saleProduct;

    public SaleHolder(SaleAdapter saleAdapter,View itemView) {
        super(itemView);
        this.saleAdapter = saleAdapter;

        saleIcon = (ImageView) itemView.findViewById(R.id.sale_icon);
        saleDelete = (ImageView) itemView.findViewById(R.id.sale_delete);
        saleProduct = (TextView) itemView.findViewById(R.id.sale_product);

        saleProduct.setSelected(true);

        saleDelete.setOnClickListener(this);
    }

    public ImageView getIcon() {
        return saleIcon;
    }

    public void setProduct(String product) {
        saleProduct.setText(product);
    }

    @Override
    public void onClick(View v) {
        Integer position = getAdapterPosition();
        final Context context = saleAdapter.getContext();
        final Product product = saleAdapter.getProduct(position);
        final Integer category = saleAdapter.getCategory(position);
        final ProgressDialog deleteDialog = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
        deleteDialog.setTitle("Delete Product");
        deleteDialog.setMessage("Processing ...");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("delete product from store").setMessage("are you sure ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteDialog.show();
                    FirebaseDatabase.getInstance().getReference().child("sale").child(ListManager.productList[category]).child(product.id).removeValue();
                    FirebaseStorage.getInstance().getReference().child("image").child(product.image).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            deleteDialog.dismiss();
                            if(task.isSuccessful()){
                                final Dialog success = new Dialog(context);
                                success.setContentView(R.layout.dialog_delete);
                                success.show();
                                saleAdapter.removeItem(getAdapterPosition());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        success.dismiss();
                                    }
                                },1000);
                            }
                        }
                    });
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // handle for cancel
                }
            }).setCancelable(false);
            builder.create().show();
    }

}
