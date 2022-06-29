package com.bootcamp.simulador.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bootcamp.simulador.databinding.ActivityDetailAcitivtyBinding
import com.bootcamp.simulador.domain.Match
import com.bumptech.glide.Glide

class DetailAcitivty : AppCompatActivity() {


    private lateinit var binding: ActivityDetailAcitivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail_acitivty)

        init()

        loadMatchFromExtra()
    }


    private fun loadMatchFromExtra() {
        intent.getParcelableExtra<Match>("Matches")?.let {

            Glide.with(this).load(it.place.image).into(binding.widgteImagePlace)
            binding.textTvDescription.text = it.description
            supportActionBar?.title = it.place.name

            Glide.with(this).load(it.homeTeam.image).into(binding.imageHomeTeam)
            binding.textNameHomeTeam.text = it.homeTeam.name
            binding.textHomeTeamScore.text = it.homeTeam.score.toString()
            binding.ratingBarHomeTeamStart.rating = it.homeTeam.strength.toFloat()
            if (it.homeTeam.score != null) {
                binding.textHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with(this).load(it.awayTeam.image).into(binding.imageAwayTeam)
            binding.textAwayNameTeam.text = it.awayTeam.name
            binding.textAwayTeamScore.text = it.awayTeam.score.toString()
            binding.raitingBarAwayTeamStars.rating = it.awayTeam.strength.toFloat()
            if (it.awayTeam.score != null) {
                binding.textAwayTeamScore.text = it.awayTeam.score.toString()
            }
        }


    }

    /**
     * Function for init the biding
     */
    private fun init() {
        binding = ActivityDetailAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}