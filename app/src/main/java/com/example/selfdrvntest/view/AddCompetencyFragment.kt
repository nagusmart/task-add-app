package com.example.selfdrvntest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfdrvntest.R
import com.example.selfdrvntest.adapter.CompetencyListAdapter
import com.example.selfdrvntest.databinding.FragmentAddCompetencyBinding
import com.example.selfdrvntest.model.Competencies
import com.example.selfdrvntest.model.Competency
import com.example.selfdrvntest.model.SelectedCompetency
import com.google.gson.Gson

class AddCompetencyFragment : Fragment() {

    lateinit var binding: FragmentAddCompetencyBinding

    lateinit var navController: NavController

    lateinit var competencyListAdapter: CompetencyListAdapter

    var competencyList = ArrayList<Competency>()

    var maxCompetencyCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddCompetencyBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setProductListAdapter();

        binding.addCompetencyRate.setOnClickListener(View.OnClickListener {

            if (maxCompetencyCount > 0) {
                var bundle = arguments
                var selectedList: ArrayList<SelectedCompetency> = ArrayList()

                for (competency in competencyList) {

                    for (competencies in competency.list) {
                        if (competencies.selectedStaus)
                            selectedList.add(
                                SelectedCompetency(
                                    competency.competency,
                                    competencies.competencies,
                                    0f
                                )
                            )
                    }
                }

                val str: String = Gson().toJson(selectedList)
                bundle?.putString("SELECTED_LIST", str)
                navController.navigate(R.id.addRateFragment, bundle)
            } else {
                showToast("Please select compentecy");
            }
        })

        binding.goBack.setOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })

    }

    private fun setProductListAdapter() {
        competencyListAdapter = CompetencyListAdapter(
            requireActivity(),
            competencyList,
            object : CompetencyListAdapter.CompetencyClickListener {
                override fun competenciesClicked(competencyPosition: Int, listPosition: Int) {

                    var competenciesStatus =
                        competencyList[competencyPosition].list[listPosition].selectedStaus

                    if (maxCompetencyCount < 3 && !competenciesStatus) {
                        competencyList[competencyPosition].list[listPosition].selectedStaus =
                            !competenciesStatus
                        competencyListAdapter.notifyItemChanged(competencyPosition)
                        maxCompetencyCount++

                    } else if (competenciesStatus) {
                        competencyList[competencyPosition].list[listPosition].selectedStaus =
                            !competenciesStatus
                        competencyListAdapter.notifyItemChanged(competencyPosition)
                        maxCompetencyCount--
                    } else {
                        Toast.makeText(
                            activity,
                            "You can select up to 3 competencies",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun showCompetenciesList(competencyPosition: Int) {
                    competencyList[competencyPosition].showCompetenciesList =
                        !competencyList[competencyPosition].showCompetenciesList
                    competencyListAdapter.notifyItemChanged(competencyPosition)
                }
            })


        val linearLayoutManager = LinearLayoutManager(activity)
        binding.competencyListRecycle.layoutManager = linearLayoutManager
        binding.competencyListRecycle.adapter = competencyListAdapter
    }


    fun setData() {

        var competenciesList1 = ArrayList<Competencies>()
        competenciesList1.add(Competencies("Transparent Communication", false))
        competenciesList1.add(Competencies("Build Management", false))
        competenciesList1.add(Competencies("Code Review", false))
        competencyList.add(Competency("Ownership", competenciesList1, false))

        var competenciesList2 = ArrayList<Competencies>()
        competenciesList2.add(Competencies("Find New Feature", false))
        competenciesList2.add(Competencies("Find New Solution", false))
        competenciesList2.add(Competencies("Find New Way", false))
        competencyList.add(Competency("Innovation", competenciesList2, false))


        var competenciesList3 = ArrayList<Competencies>()
        competenciesList3.add(Competencies("Good", false))
        competenciesList3.add(Competencies("Best", false))
        competenciesList3.add(Competencies("Excellent", false))
        competencyList.add(Competency("Trust & Integrity", competenciesList3, false))

    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}