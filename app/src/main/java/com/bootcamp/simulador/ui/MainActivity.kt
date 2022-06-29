package com.bootcamp.simulador.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.simulador.R
import com.bootcamp.simulador.data.MatchesApi
import com.bootcamp.simulador.databinding.ActivityMainBinding
import com.bootcamp.simulador.domain.Match
import com.bootcamp.simulador.ui.adapter.MatchesAdapter
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesApi
    private lateinit var matchRecyclerView: RecyclerView
    private lateinit var adapterMatches: MatchesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingButton()
    }

    /**
     * Function for init the biding
     */
    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Function for setup the recyclerView binding, and catch the matches from the api in the other function "findMatchesFromApi"
     */
    private fun setupMatchesList() {

        matchRecyclerView = binding.recyclerMatches
        matchRecyclerView.setHasFixedSize(true)
        matchRecyclerView.layoutManager = LinearLayoutManager(this)

        findMatchesFromApi()

    }

    /**
     *  Function for find the matches and setup the adapter for matches recyclerView
     */
    private fun findMatchesFromApi() {

        binding.swipeLayoutActivityMain.isRefreshing = true

        matchesApi.matches.enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    var matches: List<Match>? = response.body()

                    adapterMatches = MatchesAdapter(matches!!)

                    matchRecyclerView.adapter = adapterMatches

                } else {
                    showErrorMessage()
                }
                binding.swipeLayoutActivityMain.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMessage()
                binding.swipeLayoutActivityMain.isRefreshing = false
            }

        })
    }

    private fun setupMatchesRefresh() {
        binding.swipeLayoutActivityMain.setOnRefreshListener(this::findMatchesFromApi)

    }


    private fun showErrorMessage() {

        Toast.makeText(this, R.string.message_error_api, Toast.LENGTH_SHORT).show()
    }

    /**
     *  Function for builder the retrofit for consume the "API"
     */
    private fun setupHttpClient() {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://gabrielcontimachado2.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesApi::class.java)

    }

    /**
     *  Setup the floatingButton and algorithm for generate a random score
     */
    private fun setupFloatingButton() {

        binding.floatingSimulate.setOnClickListener {
            it.animate().rotationBy(360f).setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                        for (i in 0 until adapterMatches.itemCount step 1) {

                            var match: Match = adapterMatches.match[i]


                            match.homeTeam.score = (0..match.homeTeam.strength).random()
                            match.awayTeam.score = (0..match.awayTeam.strength).random()

                            adapterMatches.notifyItemChanged(i)
                        }
                    }
                })

        }

    }

}


