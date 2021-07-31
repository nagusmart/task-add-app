package com.example.selfdrvntest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.selfdrvntest.R
import com.example.selfdrvntest.databinding.CompetencyListItemBinding
import com.example.selfdrvntest.model.Competency
import kotlinx.android.synthetic.main.competency_list_item.view.*

class CompetencyListAdapter(
        val context: Context,
        val competencyList: List<Competency>,
        val mListener: CompetencyClickListener
) : RecyclerView.Adapter<CompetencyListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val productItemListBinding =
                CompetencyListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(productItemListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var competency = competencyList[position]

        var competencyList = competency.list

        var listSize = competencyList.size;

        holder.competencyListItemBinding?.competency?.text = competency.competency

        holder.competencyListItemBinding?.competenciesOneTxt?.text = competencyList[0].competencies

        holder.competencyListItemBinding?.competenciesTwoTxt?.text = competencyList[1].competencies

        holder.competencyListItemBinding?.competenciesThreeTxt?.text = competencyList[2].competencies

        var selectedCount = 0

        if (competencyList[0].selectedStaus) {
            holder.competencyListItemBinding?.competenciesOneImg?.setImageResource(R.drawable.ic_round_tick)
            selectedCount++;
        } else {
            holder.competencyListItemBinding?.competenciesOneImg?.setImageResource(R.drawable.ic_unselect_round)
        }

        if (competencyList[1].selectedStaus) {
            holder.competencyListItemBinding?.competenciesTwoImg?.setImageResource(R.drawable.ic_round_tick)
            selectedCount++;
        } else {
            holder.competencyListItemBinding?.competenciesTwoImg?.setImageResource(R.drawable.ic_unselect_round)
        }

        if (competencyList[2].selectedStaus) {
            holder.competencyListItemBinding?.competenciesThreeImg?.setImageResource(R.drawable.ic_round_tick)
            selectedCount++;
        } else {
            holder.competencyListItemBinding?.competenciesThreeImg?.setImageResource(R.drawable.ic_unselect_round)
        }

        holder.competencyListItemBinding?.selectedCompetenciesTxt?.text = "$selectedCount Selected"

        holder.competencyListItemBinding?.unselectedCompetenciesTxt?.text = "${listSize - selectedCount} Competencies"

        if (competency.showCompetenciesList) {
            holder.competencyListItemBinding?.showCompetencies?.visibility = View.VISIBLE
            holder.competencyListItemBinding?.showCompetenciesList?.setImageResource(R.drawable.ic_up_arrow)
        } else {
            holder.competencyListItemBinding?.showCompetencies?.visibility = View.GONE
            holder.competencyListItemBinding?.showCompetenciesList?.setImageResource(R.drawable.ic_down_arrow)
        }


        holder.competencyListItemBinding?.showCompetenciesList?.setOnClickListener(View.OnClickListener {
            if (mListener != null) {
                mListener.showCompetenciesList(position)
            }
        })

        holder.competencyListItemBinding?.selectOneCompetency?.setOnClickListener(View.OnClickListener {
            if (mListener != null) {
                mListener.competenciesClicked(position, 0)
            }
        })

        holder.competencyListItemBinding?.selectTwoCompetency?.setOnClickListener(View.OnClickListener {
            if (mListener != null) {
                mListener.competenciesClicked(position, 1)
            }
        })

        holder.competencyListItemBinding?.selectThreeCompetency?.setOnClickListener(View.OnClickListener {
            if (mListener != null) {
                mListener.competenciesClicked(position, 2)
            }
        })


    }

    override fun getItemCount(): Int {
        return competencyList.size
    }

    inner class ViewHolder(itemView: CompetencyListItemBinding) :
            RecyclerView.ViewHolder(itemView.root) {  //, View.OnClickListener
        var competencyListItemBinding: CompetencyListItemBinding? = null

        init {
            this.competencyListItemBinding = itemView
        }

    }


    interface CompetencyClickListener {
        fun competenciesClicked(competencyPosition: Int, listPosition: Int)
        fun showCompetenciesList(competencyPosition: Int)
    }

}