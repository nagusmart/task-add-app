package com.example.selfdrvntest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.selfdrvntest.databinding.CompetencyRateListItemBinding
import com.example.selfdrvntest.model.SelectedCompetency
import com.iarcuschin.simpleratingbar.SimpleRatingBar


class RatingListAdapter(
        val context: Context,
        val selectedList: List<SelectedCompetency>,
        val mListener: RatingClickListener
) : RecyclerView.Adapter<RatingListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
                CompetencyRateListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var competency = selectedList[position]

        holder.binding?.competencyTxt?.text = competency.competency
        holder.binding?.competenciesTxt?.text = competency.competencies

        holder.binding?.ratingBar?.rating = competency.rating

        holder.binding?.ratingBar?.setOnRatingBarChangeListener { simpleRatingBar, rating, fromUser ->
            if (mListener != null) {
                mListener.ratingChanged(position, rating)
            }
        }

    }

    override fun getItemCount(): Int {
        return selectedList.size
    }

    inner class ViewHolder(itemView: CompetencyRateListItemBinding) :
            RecyclerView.ViewHolder(itemView.root) {  //, View.OnClickListener
        var binding: CompetencyRateListItemBinding? = null

        init {
            this.binding = itemView
        }
    }


    interface RatingClickListener {
        fun ratingChanged(competencyPosition: Int, rating: Float)
    }

}