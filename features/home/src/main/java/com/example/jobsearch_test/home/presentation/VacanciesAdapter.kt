package com.example.jobsearch_test.home.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.VacancyItemBinding
import com.example.jobsearch_test.models.Vacancy
import java.text.SimpleDateFormat
import java.util.Locale

class VacanciesAdapter(
    private val itemVacancyClick: (String) -> Unit,
    private val itemFavoriteClick: (String) -> Unit,
    private val context: Context
): ListAdapter<Vacancy, RecyclerView.ViewHolder>(VacancyDiffCallback()) {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
    val monthFormat = SimpleDateFormat("d MMMM", Locale("ru"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VacancyViewHolder(binding = VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as Vacancy
        val viewHolder = holder as VacancyViewHolder
        viewHolder.bind(item)
    }

    inner class VacancyViewHolder(private val binding: VacancyItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(vacancy: Vacancy) {

            binding.lookingNumber.text = "${context.getString(com.example.ui.R.string.now_looking)} " +
                    "${vacancy.lookingNumber} ${context.getString(com.example.ui.R.string.people)}"
            binding.favoriteIcon.setImageResource(
                if (vacancy.isFavorite) com.example.ui.R.drawable.ic_favorites_select
                else com.example.ui.R.drawable.ic_favorite
            )
            binding.title.text = vacancy.title

            if (vacancy.salary.short.isNullOrEmpty()) binding.salary.visibility = View.GONE
            else binding.salary.text = vacancy.salary.short
            binding.city.text = vacancy.address.town
            binding.company.text = vacancy.company
            binding.experience.text = vacancy.experience.previewText
            binding.publishedDate.text = vacancy.publishedDate
            binding.publishedDate.text = "${context.getString(com.example.ui.R.string.published)} " +
                    "${monthFormat.format(dateFormat.parse(vacancy.publishedDate))}"
            binding.root.setOnClickListener { itemVacancyClick.invoke(vacancy.id) }
            binding.favoriteIcon.setOnClickListener { itemFavoriteClick.invoke(vacancy.id) }
        }
    }

    internal class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>(){
        override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem.id() == newItem.id()
        override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem.content(newItem)
        override fun getChangePayload(oldItem: Vacancy, newItem: Vacancy): Any {
            return oldItem.payload(newItem)
        }
    }
}