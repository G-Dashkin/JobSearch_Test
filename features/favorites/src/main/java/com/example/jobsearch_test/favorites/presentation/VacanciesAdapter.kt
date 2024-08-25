package com.example.jobsearch_test.favorites.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.favorites.databinding.VacancyItemBinding
import com.example.jobsearch_test.models.Vacancy
import java.text.SimpleDateFormat
import java.util.Locale

class VacanciesAdapter(
    private val itemFavoriteClick: (String) -> Unit
): ListAdapter<Vacancy, RecyclerView.ViewHolder>(VacancyDiffCallback()) {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
    val monthFormat = SimpleDateFormat("MM MMMM", Locale("ru"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VacancyViewHolder(binding = VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as Vacancy
        val viewHolder = holder as VacancyViewHolder
        viewHolder.bind(item)
    }

    inner class VacancyViewHolder(private val binding: VacancyItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(vacancy: Vacancy) {

            binding.lookingNumber.text = "Сейчас просматривает ${vacancy.lookingNumber} человек"
            binding.title.text = vacancy.title

            if (vacancy.salary.short.isNullOrEmpty()) binding.salary.visibility = View.GONE
            else binding.salary.text = vacancy.salary.short
            binding.city.text = vacancy.address.town
            binding.company.text = vacancy.company
            binding.experience.text = vacancy.experience.previewText
            binding.publishedDate.text = vacancy.publishedDate
            binding.publishedDate.text = "Опубликовано ${monthFormat.format(dateFormat.parse(vacancy.publishedDate))}"

            binding.favoriteIcon.setOnClickListener { itemFavoriteClick.invoke(vacancy.id) }
        }
    }

    internal class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>(){
        override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem == newItem
        override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
            oldItem.id == newItem.id
    }
}