package com.example.livedata;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Mario {

    interface MarioListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> accion;

    void iniciarAccion(MarioListener marioListener) {
        if (accion == null || accion.isCancelled()) {
            accion = scheduler.scheduleAtFixedRate(new Runnable() {
                int marioAccion;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones < 0) {
                        repeticiones = random.nextInt(3) + 3;
                        marioAccion = random.nextInt(4) + 1;
                    }
                    if (marioAccion == 1 || marioAccion == 2){
                        marioListener.cuandoDeLaOrden("Mario" + marioAccion + ":" + (repeticiones == 0 ? "marioBailando" : accion));
                    } else{
                        marioListener.cuandoDeLaOrden("Mario" + marioAccion + ":" + (repeticiones == 0 ? "marioConduciendo" : accion));
                    }

                    repeticiones--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararAccion() {
        if (accion != null) {
            accion.cancel(true);
        }
    }

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarAccion(new MarioListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararAccion();
        }
    };

}
