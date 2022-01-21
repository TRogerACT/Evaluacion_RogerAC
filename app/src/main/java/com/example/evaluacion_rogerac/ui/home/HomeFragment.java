package com.example.evaluacion_rogerac.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaluacion_rogerac.R;
import com.example.evaluacion_rogerac.databinding.FragmentHomeBinding;
import com.example.evaluacion_rogerac.service.adapter.CuentasAdapter;
import com.example.evaluacion_rogerac.service.entity.service.Cuentas;
import com.example.evaluacion_rogerac.service.viewmodel.CuentasViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private CuentasViewModel cuentasViewModel;
    private RecyclerView Cuentass;
    private CuentasAdapter adapter;
    private List<Cuentas> cuentas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v){
        ViewModelProvider vmp = new ViewModelProvider(this);
        Cuentass = v.findViewById(R.id.Cuentass);
        Cuentass.setLayoutManager(new GridLayoutManager(getContext(), 1));
        cuentasViewModel = vmp.get(CuentasViewModel.class);

    }
    private void initAdapter() {
        adapter = new CuentasAdapter(cuentas);
        Cuentass.setAdapter(adapter);
    }
    private void loadData() {
        cuentasViewModel.listarCuentas().observe(getViewLifecycleOwner(), response -> {
            adapter.cargarItems(response.getBody());
        });
    }

}