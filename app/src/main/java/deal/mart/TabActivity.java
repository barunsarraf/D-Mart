package deal.mart;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TabActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private ViewPager viewPager;
    private TabManager tabManager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabFragmentAdapter fragmentAdapter;
    private SearchView searchView;
    private Dialog searchDialog;
    private ListView searchList;
    private ArrayList<Pair<Integer,Integer>> pairList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        toolbar = (Toolbar) findViewById(R.id.tab_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        tabManager = new TabManager(this);
        fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(),tabManager);

        viewPager = (ViewPager) findViewById(R.id.tab_view_pager);
        viewPager.setAdapter(fragmentAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_tab_layout);

        setupTab();
        setupSearchList();
    }

    private void setupTab(){
        tabLayout.setupWithViewPager(viewPager);
        Integer tabIcon [] = {R.drawable.tab_book,R.drawable.tab_cycle,R.drawable.tab_laptop,R.drawable.tab_smartphone,R.drawable.tab_watch,R.drawable.tab_other};
        for(Integer index =0 ;index < tabLayout.getTabCount() ; index++)
            tabLayout.getTabAt(index).setIcon(tabIcon[index]);
    }

    private void setupSearchList(){
        searchDialog = new Dialog(this);
        searchDialog.setContentView(R.layout.dialog_list);
        searchList = (ListView) searchDialog.findViewById(R.id.search_list);
        searchList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab,menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("search product");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showSearchList(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void showSearchList(String query){

        pairList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        for(Integer row = 0 ; row < tabManager.getAllAdapter().size() ; row++){
            TabAdapter adapter = tabManager.getAdapter(row);
            for(Integer column = 0 ; column < adapter.getItemCount() ; column++){
                Product product = (Product) adapter.getItem(column);
                if(product.product.toLowerCase().contains(query.toLowerCase()) || product.description.toLowerCase().contains(query.toLowerCase())) {
                    arrayList.add(product.product);
                    pairList.add(Pair.create(row,column));
                }
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.search_row,R.id.search_text,arrayList);
        searchList.setAdapter(arrayAdapter);
        if(query.length() > 5)
            query = query.substring(0,4)+"...";
        searchDialog.setTitle("Results for " + query + "    -    " + "total  " + arrayList.size());
        if(arrayList.size() == 0)
            arrayList.add("No product available");
        searchDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pair<Integer,Integer> pair = pairList.get(position);
        startActivity(new Intent(this,ViewActivity.class).putExtra("ROW",pair.first).putExtra("COLUMN",pair.second));
    }
}
