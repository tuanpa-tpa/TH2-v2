package com.example.th2_v2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.th2_v2.R;
import com.example.th2_v2.data.db.SQLiteHelper;
import com.example.th2_v2.data.model.Item;
import com.example.th2_v2.process.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    Button btSearch, btnStatistic;
    EditText etSearch;
    TextView txtStatistic;

    RecyclerView recyclerView;
    List<Item> itemList;
    List<Item> itemListSearch;
    ItemAdapter adapter;


    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_third, container, false);
        btSearch = v.findViewById(R.id.btSearch);
        etSearch = v.findViewById(R.id.et_search);
        recyclerView = v.findViewById(R.id.rv_search);
        btnStatistic = v.findViewById(R.id.bt_thong_ke);
        txtStatistic = v.findViewById(R.id.txt_statistic);
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initListener();
    }

    private void initView() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getContext());
        itemList = sqLiteHelper.getAll();
        itemListSearch = new ArrayList<>();
        itemListSearch.addAll(itemList);

        adapter = new ItemAdapter(getContext(), itemListSearch);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListSearch.clear();
                String searchText = etSearch.getText().toString();
                for (Item item : itemList){
                    if(item.getName().toLowerCase().contains(searchText.toLowerCase())
                    ||item.getDetail().toLowerCase().contains(searchText.toLowerCase() ))
                        itemListSearch.add(item);
                }

                adapter.notifyDataSetChanged();
            }
        });

//        btThongKe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map m = new HashMap<String, Integer>();
//
//                for(Item item : itemList){
//                    if(m.get(item.getStatus()) == null){
//                        m.put(item.getStatus(), 0);
//                    }
//
//                    m.put(item.getStatus(), Integer.parseInt(String.valueOf(m.get(item.getStatus()))) + 1);
//                    Log.d("Thong ke", item.getStatus() + " " +m.get(item.getStatus()) + "");
//                }
//
//
//            }
//        });

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStatistic.getText().toString().compareToIgnoreCase("Hide Report") == 0){
                    txtStatistic.setText("");
                    btnStatistic.setText("View Report");
                }
                else{
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getContext());
                    String res = sqLiteHelper.getStatistic();
                    txtStatistic.setText(res);
                    btnStatistic.setText("Hide Report");
                }
            }
        });
    }
}