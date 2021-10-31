package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class MarioViewModel extends AndroidViewModel {
    Mario mario;

    LiveData<Integer> marioAccionLiveData;
    LiveData<String> accionesLiveData;

    public MarioViewModel(@NonNull Application application) {
        super(application);

        mario = new Mario();

        marioAccionLiveData = Transformations.switchMap(mario.ordenLiveData, new Function<String, LiveData<Integer>>() {

            String marioAccionAnterior;

            @Override
            public LiveData<Integer> apply(String orden) {

                String accion = orden.split(":")[0];

                if(!accion.equals(marioAccionAnterior)){
                    marioAccionAnterior = accion;
                    int imagen;
                    switch (accion) {
                        case "Mario1":
                        default:
                            imagen = R.drawable.mario;
                            break;
                        case "Mario2":
                            imagen = R.drawable.mario2;
                            break;
                        case "Mario3":
                            imagen = R.drawable.mario3;
                            break;
                        case "Mario4":
                            imagen = R.drawable.mario4;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        accionesLiveData = Transformations.switchMap(mario.ordenLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }

    LiveData<Integer> obtenerEjercicio(){
        return marioAccionLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return accionesLiveData;
    }
}
