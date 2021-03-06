package com.app.covidstats.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.covidstats.R;
import com.app.covidstats.adapter.StatsAdapter;
import com.app.covidstats.base.BaseFragment;
import com.app.covidstats.databinding.FragmentStatsBinding;

public class StatsFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private StatsAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        FragmentStatsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false);

        recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = binding.refresh;

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        binding.setViewModel(mainViewModel);
        //
        adapter = new StatsAdapter();
        mainViewModel.setAdapter(adapter);
        mainViewModel.getStats();

        mainViewModel.getToasMessage().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s + " " + " data load offline!!", Toast.LENGTH_SHORT).show();
            }
        });

//        mainViewModel.getIsLoading().observe(this.getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean s) {
//                swipeRefreshLayout.setRefreshing(s);
//            }
//        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
