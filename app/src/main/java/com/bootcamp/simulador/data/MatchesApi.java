package com.bootcamp.simulador.data;

import com.bootcamp.simulador.domain.Match;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesApi {

    @GET("matches.json")
    Call<List<Match>> getMatches();

}
