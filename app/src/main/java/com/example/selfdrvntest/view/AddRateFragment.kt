package com.example.selfdrvntest.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfdrvntest.R
import com.example.selfdrvntest.adapter.RatingListAdapter
import com.example.selfdrvntest.databinding.FragmentAddRateBinding
import com.example.selfdrvntest.model.SelectedCompetency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddRateFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentAddRateBinding

    lateinit var navController: NavController

    lateinit var ratingListAdapter: RatingListAdapter

    var ratingList: ArrayList<SelectedCompetency> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddRateBinding.inflate(inflater, container, false)

        var data = arguments?.getString("SELECTED_LIST")

        val itemType = object : TypeToken<ArrayList<SelectedCompetency>>() {}.type
        ratingList = Gson().fromJson<ArrayList<SelectedCompetency>>(data, itemType)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setRatingListAdapter();

        binding.next.setOnClickListener(View.OnClickListener {

            var send = arguments

            val str: String = Gson().toJson(ratingList)

            send?.putString("ScreenThreeData", str);

            navController.navigate(R.id.addTaskFragment, send)
        })

        binding.goBack.setOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })

    }


    private fun setRatingListAdapter() {
        ratingListAdapter = RatingListAdapter(
            requireActivity(),
            ratingList,
            object : RatingListAdapter.RatingClickListener {
                override fun ratingChanged(competencyPosition: Int, rating: Float) {

                    if (!binding.competencyRateRecycle.isComputingLayout && binding.competencyRateRecycle.scrollState == SCROLL_STATE_IDLE) {
                        ratingList[competencyPosition].rating = rating
                        ratingListAdapter.notifyItemChanged(competencyPosition)
                    }
                }
            })

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.competencyRateRecycle.layoutManager = linearLayoutManager
        binding.competencyRateRecycle.adapter = ratingListAdapter
    }

}