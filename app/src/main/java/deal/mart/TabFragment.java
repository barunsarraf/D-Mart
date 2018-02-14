package deal.mart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private TabAdapter tabAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(tabAdapter);
        return view;
    }

    public void setAdapter(TabAdapter tabAdapter){
        this.tabAdapter = tabAdapter;
    }
    public void setContext(Context context){
        this.context = context;
    }
}
