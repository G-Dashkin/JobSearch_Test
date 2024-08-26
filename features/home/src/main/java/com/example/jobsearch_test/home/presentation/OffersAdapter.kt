package com.example.jobsearch_test.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.OfferItemBinding
import com.example.jobsearch_test.models.Offer

private const val NEAR_VACANCIES = "near_vacancies"
private const val LEVEL_UP_RESUME = "level_up_resume"
private const val TEMPORARY_JOB = "temporary_job"

class OffersAdapter(private val offersList: List<Offer>):
    RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    class OfferViewHolder(private val biding: OfferItemBinding): RecyclerView.ViewHolder(biding.root) {
        val offerIcon = biding.offerIcon
        val upButton = biding.offerBottom
        val offerText = biding.offerText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(biding = OfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        when(offersList[position].id) {
            NEAR_VACANCIES -> holder.offerIcon.setImageResource(com.example.ui.R.drawable.ic_offer_geo)
            LEVEL_UP_RESUME -> {
                holder.offerIcon.setImageResource(com.example.ui.R.drawable.ic_offer_star)
                holder.upButton.visibility = View.VISIBLE
            }
            TEMPORARY_JOB -> holder.offerIcon.setImageResource(com.example.ui.R.drawable.ic_offer_check)
        }
        holder.offerText.text = offersList[position].title.trim()
    }

    override fun getItemCount(): Int = offersList.size
}