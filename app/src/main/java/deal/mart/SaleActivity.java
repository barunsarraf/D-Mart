package deal.mart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SaleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SaleAdapter saleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        recyclerView = (RecyclerView) findViewById(R.id.sale_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        setupAdapter();
        recyclerView.setAdapter(saleAdapter);
    }

    private void setupAdapter() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Integer> cats = new ArrayList<>();
        Integer cat = 0;
        for(ListAdapter listAdapter : ListManager.getAllAdapter()){
            for(Integer count = 0 ; count < listAdapter.getItemCount() ; count++ ){
                Product product = (Product) listAdapter.getItem(count);
                if( product.seller.equals(SignInActivity.getUserName())) {
                    products.add(product);
                    cats.add(cat);
                }
            }
            cat++;
        }
        saleAdapter = new SaleAdapter(this,products,cats);
    }
}
