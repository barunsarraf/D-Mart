package deal.mart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Admin on 10/9/2017.
 */

public class TabHolder extends RecyclerView.ViewHolder {

    private ImageView icon;
    private TextView product,price,description,date;
    private RatingBar ratingBar;


    public TabHolder(View itemView) {
        super(itemView);
        product = (TextView) itemView.findViewById(R.id.tab_product_name);
        price = (TextView) itemView.findViewById(R.id.tab_product_price);
        description = (TextView) itemView.findViewById(R.id.tab_product_description);
        date = (TextView) itemView.findViewById(R.id.tab_product_date);

        icon = (ImageView) itemView.findViewById(R.id.tab_product_icon);
        ratingBar = (RatingBar) itemView.findViewById(R.id.tab_product_rating);

        product.setSelected(true);
        price.setSelected(true);
        description.setSelected(true);
        date.setSelected(true);

    }

    public void setIcon(ImageView icon) {
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setProduct(String product) {
        this.product.setText(product);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
    public void setDate(String date){
        this.date.setText(date);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }

    public void setRating(Float rating) {
        this.ratingBar.setRating(rating);
    }
}
