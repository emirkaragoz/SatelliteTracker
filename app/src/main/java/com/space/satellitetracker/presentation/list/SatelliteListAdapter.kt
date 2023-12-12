package com.space.satellitetracker.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.space.satellitetracker.R
import com.space.satellitetracker.databinding.ItemSatelliteBinding
import com.space.satellitetracker.domain.model.Satellite

class SatelliteListAdapter: RecyclerView.Adapter<SatelliteListAdapter.SatelliteListViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Satellite>() {
        override fun areItemsTheSame(oldItem: Satellite, newItem: Satellite): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Satellite, newItem: Satellite): Boolean {
            return oldItem == newItem
        }
    }

    val listDiffer = AsyncListDiffer(this, differCallback)

    inner class SatelliteListViewHolder(private val binding: ItemSatelliteBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(position: Int) {
            val item = listDiffer.currentList[position]
            binding.name.text = item.name
            if (item.active) {
                binding.name.setTextColor(context.getColor(R.color.black))
                binding.statusText.setTextColor(context.getColor(R.color.black))
                binding.statusText.text = context.getString(R.string.active)
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.bg_active_status_indicator)
            } else {
                binding.name.setTextColor(context.getColor(R.color.gray))
                binding.statusText.setTextColor(context.getColor(R.color.gray))
                binding.statusText.text = binding.root.context.getString(R.string.passive)
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.bg_passive_status_indicator)
            }
            if (position == itemCount-1) { //last item
                binding.separator.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteListViewHolder {
        return SatelliteListViewHolder(
            ItemSatelliteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    override fun onBindViewHolder(holder: SatelliteListViewHolder, position: Int) {
        holder.bind(position)
    }
}