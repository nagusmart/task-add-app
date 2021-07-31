package com.example.selfdrvntest.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.selfdrvntest.R
import com.example.selfdrvntest.databinding.CompetencyRateListItemBinding
import com.example.selfdrvntest.databinding.SelectedCompetencyItemBinding
import com.example.selfdrvntest.model.SelectedCompetency
import com.iarcuschin.simpleratingbar.SimpleRatingBar


class ShowSelectedCompetencyAdapter(
    val context: Context,
    val selectedList: List<SelectedCompetency>
) : RecyclerView.Adapter<ShowSelectedCompetencyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            SelectedCompetencyItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var competency = selectedList[position]

        holder.binding?.competencyName?.text = competency.competencies

        holder.binding?.rateCount?.text = "" + competency.rating

        when (position) {
            0 -> {
                holder.binding?.competencyName?.setTextColor(Color.parseColor("#3cd095"))
                holder.binding?.rateCount?.setTextColor(Color.parseColor("#3cd095"))
                holder.binding?.rateImg?.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.light_grean
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                );
                holder.binding?.viewBg?.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#baefda"))
            }
            1 -> {
                holder.binding?.competencyName?.setTextColor(Color.parseColor("#f78e41"))
                holder.binding?.rateCount?.setTextColor(Color.parseColor("#f78e41"))
                holder.binding?.rateImg?.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.light_yello
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                );
                holder.binding?.viewBg?.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#fed3b3"))
            }
            2 -> {
                holder.binding?.competencyName?.setTextColor(Color.parseColor("#fe4f4f"))
                holder.binding?.rateCount?.setTextColor(Color.parseColor("#fe4f4f"))
                holder.binding?.rateImg?.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.light_red
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                );
                holder.binding?.viewBg?.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#fdd2d2"))
            }
        }
    }

    override fun getItemCount(): Int {
        return selectedList.size
    }

    inner class ViewHolder(itemView: SelectedCompetencyItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {  //, View.OnClickListener
        var binding: SelectedCompetencyItemBinding? = null

        init {
            this.binding = itemView
        }
    }


}