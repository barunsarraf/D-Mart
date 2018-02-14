package deal.mart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 9/30/2017.
 */
public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    private TextView product,price;
    private ImageView image;
    private int ROW;

    public void setROW(int ROW) {
        this.ROW = ROW;
    }


    public ListHolder(View itemView) {
        super(itemView);
        product = (TextView) itemView.findViewById(R.id.product_name);
        price = (TextView) itemView.findViewById(R.id.product_price);

        product.setSelected(true);

        image = (ImageView) itemView.findViewById(R.id.product_image);
        image.setOnClickListener(this);

    }

    public void setProduct(String product) {
        this.product.setText(product);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }

    public ImageView getImage(){
        return image;
    }

    public void setImage(String image) {
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context,ViewActivity.class).putExtra("ROW",ROW).putExtra("COLUMN",this.getAdapterPosition()));
    }
}
