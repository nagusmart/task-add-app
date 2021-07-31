package com.example.selfdrvntest.view

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfdrvntest.R
import com.example.selfdrvntest.adapter.ShowSelectedCompetencyAdapter
import com.example.selfdrvntest.databinding.FragmentAddTaskBinding
import com.example.selfdrvntest.model.SelectedCompetency
import com.example.selfdrvntest.model.StorageModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding

    lateinit var navController: NavController

    lateinit var showSelectedCompetencyAdapter: ShowSelectedCompetencyAdapter

    var profileBitmap: Bitmap? = null

    var ratingList: ArrayList<SelectedCompetency> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setBundelData()

        addCompetency()

        allClickEvent()

        setRatingListAdapter()

        backStackListener()
    }

    private fun backStackListener() {
        activity?.onBackPressedDispatcher?.addCallback(
                requireActivity(),
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        goBack()
                    }
                })
    }

    private fun goBack() {
        val navHostFragment = this.parentFragment as NavHostFragment?
        if (navHostFragment != null &&
                navHostFragment.childFragmentManager.backStackEntryCount == 0
        ) {
            requireActivity().finish()
        } else {

            var lable = NavHostFragment.findNavController(this).currentDestination?.label

            if (lable?.equals("fragment_add_task") == true) {
                requireActivity().finish()
            } else {
                NavHostFragment.findNavController(this).navigateUp()
            }

        }
    }

    private fun allClickEvent() {
        binding.dateTxt.setOnClickListener(View.OnClickListener { setTaskDate() })

        binding.addCompetency.setOnClickListener(View.OnClickListener {
            var storageModel: StorageModel = StorageModel(
                    binding.employeName.text.toString(),
                    binding.taskTitle.text.toString(),
                    profileBitmap,
                    binding.dateTxt.text.toString(),
                    binding.feedBackEdt.text.toString()
            )
            val str: String = Gson().toJson(storageModel)

            var bundle = Bundle()
            bundle.putString("ScreenOneData", str);

            navController.navigate(R.id.addCompetencyFragment, bundle)
        })

        binding.confirmationCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            setConfirmationChanges(isChecked)
        }

        binding.addTask.setOnClickListener(View.OnClickListener {

            if (binding.taskTitle.text.toString() == "") {
                showToast("Please enter task title")
            } else if (binding.dateTxt.text.toString() == "") {
                showToast("Select task date")
            } else if (ratingList.size == 0) {
                showToast("Select the competency")
            } else if (binding.feedBackEdt.text.toString() == "") {
                showToast("Message should not be empty")
            } else if (!binding.confirmationCheck.isChecked) {
                showToast("Conform send anonymous feedback")
            } else {
                showToast("SuccessFully")
            }
        })

        binding.clearData.setOnClickListener(View.OnClickListener {
            clearData()
        })

        binding.employeImg.setOnClickListener(View.OnClickListener {

            PickImageDialog.build(PickSetup())
                    .setOnPickResult {
                        binding.employeImg.setImageBitmap(it.bitmap)
                        profileBitmap = it.bitmap
                    }
                    .setOnPickCancel {

                    }.show(requireActivity().getSupportFragmentManager())

        })
    }

    private fun clearData() {
        if (arguments != null) {
            arguments?.clear()
        }
        ratingList.clear()
        showSelectedCompetencyAdapter.notifyDataSetChanged()
        addCompetency()
        refreshUi()
        binding.confirmationCheck.isChecked = false
        setConfirmationChanges(false)
    }

    private fun refreshUi() {
        binding.taskTitle.text.clear()
        binding.dateTxt.text = ""
        binding.feedBackEdt.text.clear()
        binding.employeImg.setImageResource(R.drawable.defaule_icon);
    }

    private fun setBundelData() {
        if (arguments != null) {

            var data = arguments?.getString("ScreenOneData")

            val itemType = object : TypeToken<StorageModel>() {}.type
            var storageModel = Gson().fromJson<StorageModel>(data, itemType)
            if (storageModel != null) {
                binding.employeName.text = storageModel.name

                (binding.taskTitle as TextView).text = storageModel.taskTitle

                binding.dateTxt.text = storageModel.date

                (binding.feedBackEdt as TextView).text = storageModel.message

                if (storageModel.imageBitmap != null)
                    binding.employeImg.setImageBitmap(storageModel.imageBitmap)
            }
            if (arguments?.getString("ScreenThreeData") != null) {

                var data = arguments?.getString("ScreenThreeData")

                val itemType = object : TypeToken<ArrayList<SelectedCompetency>>() {}.type
                ratingList = Gson().fromJson<ArrayList<SelectedCompetency>>(data, itemType)
            }
        }
    }

    private fun setConfirmationChanges(isChecked: Boolean) {
        if (isChecked) {
            binding?.addTask?.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#0160fe"))
            binding.addTask.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            binding?.addTask?.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#c7d6ec"))
            binding.addTask.setTextColor(Color.parseColor("#2f7dff"))
        }
    }

    private fun addCompetency() {
        if (ratingList.size > 0) {
            binding.addCompetency.visibility = View.GONE
        } else {
            binding.addCompetency.visibility = View.VISIBLE
        }
    }

    private fun setTaskDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val months: Array<String> = DateFormatSymbols().getMonths()
                    binding.dateTxt.setText("" + dayOfMonth + " " + months[monthOfYear] + " " + year)
                },
                year,
                month,
                day
        )

        dpd.show()

    }

    private fun setRatingListAdapter() {
        showSelectedCompetencyAdapter = ShowSelectedCompetencyAdapter(
                requireActivity(),
                ratingList
        )
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.selectedCompetencyRecycle.layoutManager = linearLayoutManager
        binding.selectedCompetencyRecycle.adapter = showSelectedCompetencyAdapter
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
    }


}