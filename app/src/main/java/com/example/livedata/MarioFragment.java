package com.example.livedata;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.livedata.databinding.FragmentMarioBinding;



public class MarioFragment extends Fragment {

    private FragmentMarioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMarioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MarioViewModel marioViewModel = new ViewModelProvider(this).get(MarioViewModel.class);

        marioViewModel.obtenerEjercicio().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer ejercicio) {
                Glide.with(MarioFragment.this).load(ejercicio).into(binding.accionMario);
            }
        });

        marioViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeticion) {
                if(repeticion.equals("marioBailando")){
                    binding.marioBailando.setVisibility(View.VISIBLE);
                }if(repeticion.equals("marioConduciendo")){
                    binding.marioConduciendo.setVisibility(View.VISIBLE);
                }else {
                    binding.marioBailando.setVisibility(View.GONE);
                    binding.marioConduciendo.setVisibility(View.GONE);
                }
                binding.repeticion.setText("Mario De Parranda");
            }

        });
    }
}