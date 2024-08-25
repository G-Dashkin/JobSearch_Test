package com.example.jobsearch_test.vacancy.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jobsearch_test.core.contracts.CALL_BOTTOM_MENU_LISTENER
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.models.Vacancy
import com.example.jobsearch_test.vacancy.di.DaggerVacancyComponent
import com.example.jobsearch_test.vacancy.di.VacancyFeatureDepsProvider
import com.example.vacancy.R
import com.example.vacancy.databinding.CustomDialogBinding
import com.example.vacancy.databinding.FragmentVacancyBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val VACANCY_ID_EXTRA_PARAM = "VACANCY_ID_EXTRA_PARAM"

class VacancyFragment: Fragment(R.layout.fragment_vacancy) {

    companion object {
        fun getInstance(vacancyId: String): VacancyFragment {
            return  VacancyFragment().apply {
                val bundle = Bundle()
                bundle.putString(VACANCY_ID_EXTRA_PARAM, vacancyId)
                arguments = bundle
            }
        }
    }

    private lateinit var binding: FragmentVacancyBinding
    private lateinit var bindingCustomDialog: CustomDialogBinding

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var vmFactoryAssisted: VacancyViewModelFactoryAssisted

    private val vacancyViewModel by viewModels<VacancyViewModel> {
        vmFactoryAssisted.create(arguments?.getString(VACANCY_ID_EXTRA_PARAM))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerVacancyComponent
            .builder()
            .addDeps(VacancyFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVacancyBinding.bind(view)

        binding.arrowBack.setOnClickListener { vacancyViewModel.arrowBackClicked() }
        setState()
        setVacancyData()
    }

    private fun setState() {
        vacancyViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                VacancyScreen.Loading -> showLoadingIndicator()
                is VacancyScreen.Loaded -> setVacancyData()
                VacancyScreen.Back -> showBackFragment()
                is VacancyScreen.Apply -> showApplyDialog(vacancyTitle = it.vacancyTitle)
            }
        }
    }


    private fun setVacancyData() {
        vacancyViewModel.vacancy.observe(viewLifecycleOwner) { vacancy ->
            binding.circularProgressIndicator.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
            binding.title.text = vacancy.title
            binding.salary.text = if (vacancy.salary.short == null) vacancy.salary.full else vacancy.salary.short
            binding.experience.text = "Требуемый опыт ${vacancy.experience.previewText}"
            binding.schedules.text = vacancy.schedules.toString()

            binding.appliedNumber.text = "${vacancy.appliedNumber} человек уже откликнулись"
            binding.lookingNumber.text = "${vacancy.lookingNumber} человек сейчас смотрят"

            binding.favoriteIcon.setImageResource(
                if (vacancy.isFavorite) com.example.ui.R.drawable.ic_favorites_select
                else com.example.ui.R.drawable.ic_favorite
            )

            binding.company.text = vacancy.company
            binding.companyAddress.text = "${vacancy.address.town}, ${vacancy.address.street}, ${vacancy.address.house}"
            binding.description.text = vacancy.description
            binding.responsibilities.text = vacancy.responsibilities
            binding.favoriteIcon.setOnClickListener { changeIsFavoriteIcon(vacancy) }
            binding.applyButton.setOnClickListener { vacancyViewModel.applyButtonClicked(vacancy.title) }
            binding.questionsList.adapter = QuestionsListAdapter(requireContext(), vacancy.questions)
            setListViewSettings()
        }
    }

    private fun showApplyDialog(vacancyTitle: String) {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        bindingCustomDialog = CustomDialogBinding.inflate(inflater)
        dialog.setContentView(bindingCustomDialog.root)
        dialog.show()

        bindingCustomDialog.title.text = vacancyTitle
        bindingCustomDialog.addCoverLetter.setOnClickListener {
            bindingCustomDialog.coverLetterField.visibility = View.VISIBLE
            bindingCustomDialog.addCoverLetter.visibility = View.GONE
        }
        bindingCustomDialog.applyButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun changeIsFavoriteIcon(vacancy: Vacancy) {
        var isFavorite = vacancy.isFavorite
        lifecycleScope.launch {
            vacancyViewModel.selectFavoriteIcon(vacancyId = vacancy.id)
            // вот это убрать...
            delay(200)

            parentFragmentManager.setFragmentResult(CALL_BOTTOM_MENU_LISTENER, bundleOf())
            if (isFavorite) {
                binding.favoriteIcon.setImageResource(com.example.ui.R.drawable.ic_favorite)
                isFavorite = false
            } else {
                binding.favoriteIcon.setImageResource(com.example.ui.R.drawable.ic_favorites_select)
                isFavorite = true
            }
        }
    }
    private fun setListViewSettings(){
        var totalHeight = 0
        for (i in 0 until binding.questionsList.adapter.count) {
            val mView = binding.questionsList.adapter.getView(i, null, binding.questionsList)
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            totalHeight += mView.measuredHeight
        }
        val params = binding.questionsList.layoutParams
        params.height = totalHeight + (binding.questionsList.dividerHeight * (binding.questionsList.adapter.count - 1))
        binding.questionsList.layoutParams = params
    }

    private fun showBackFragment(){
        router.back()
    }


    private fun showLoadingIndicator() {
        binding.circularProgressIndicator.visibility = View.VISIBLE
    }
}