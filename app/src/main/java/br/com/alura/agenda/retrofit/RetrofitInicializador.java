package br.com.alura.agenda.retrofit;

import br.com.alura.agenda.services.AlunoService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by renan on 07/02/2018.
 */

public class RetrofitInicializador {
    private final Retrofit retrofit;
    public RetrofitInicializador(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.21.100.32:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AlunoService getAlunoService() {

        return retrofit.create(AlunoService.class);
    }
}
