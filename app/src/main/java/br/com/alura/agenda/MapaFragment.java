package br.com.alura.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by renan on 05/02/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this); //o mapa ja esta pronto, trate-o. O onMapReady é chamado pelo getMapAsync
    }

    @Override
    public void onMapReady(GoogleMap googleMap) { //chamado pelo getMapAsync


        LatLng posicaoDaEscola = pegaCoodenadaDoEndereco("Rua Vergueiro 3185, Vila Mariana, Sao Paulo");
        if(posicaoDaEscola != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 17); //especificar uma lagitude e longetude no mapa e um nivel de Zoom
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());

        for(Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng coordenada = pegaCoodenadaDoEndereco(aluno.getEndereco());
            if(coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();


        new Localizador(getContext(), googleMap);

    }


    private LatLng pegaCoodenadaDoEndereco(String endereco){


        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());

                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
