package com.bootcamp.simulador.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.simulador.R
import com.bootcamp.simulador.databinding.CardviewMatchesBinding
import com.bootcamp.simulador.domain.Match
import com.bootcamp.simulador.ui.DetailAcitivty
import com.bootcamp.simulador.ui.MainActivity
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MatchesAdapter(var match: List<Match>) :
    RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {

        val matchList =
            CardviewMatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MatchViewHolder(matchList)

    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {

        var context: Context = holder.itemView.context

        holder.homeTeam.text = match[position].homeTeam.name
        holder.awayTeam.text = match[position].awayTeam.name

        if (match[position].homeTeam.score != null) {
            holder.scoreHomeTeam.text = match[position].homeTeam.score.toString()
        }
        if (match[position].awayTeam.score != null) {
            holder.scoreAwayTeam.text = match[position].awayTeam.score.toString()
        }

        Glide.with(context).load(match[position].homeTeam.image).into(holder.imageHomeTeam)
        Glide.with(context).load(match[position].awayTeam.image).into(holder.imageAwayTeam)

        holder.itemView.setOnClickListener {
            var intent: Intent = Intent(holder.itemView.context, DetailAcitivty:: class.java)
            intent.putExtra("Matches", match[position])
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int = match.size


    inner class MatchViewHolder(binding: CardviewMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val homeTeam: TextView = binding.textCardNameFirstTeam
        val awayTeam: TextView = binding.textCardNameSecondTeam
        val scoreHomeTeam: TextView = binding.textCardScoreFirstTeam
        val scoreAwayTeam: TextView = binding.textCardScoreSecondTeam
        val imageHomeTeam: CircleImageView = binding.circleImageCardFirstTeam
        val imageAwayTeam: CircleImageView = binding.circleImageCardSecondTeam

    }
}