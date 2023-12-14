package com.space.satellitetracker.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.space.satellitetracker.R
import com.space.satellitetracker.databinding.FragmentDetailBinding
import com.space.satellitetracker.util.collectLatestLifecycleFlow
import com.space.satellitetracker.util.convertTime
import com.space.satellitetracker.util.setBoldSpannable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class DetailFragment: Fragment() {

    private lateinit var viewBinding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(inflater,container,false)
        viewModel.getDetail(args.satelliteId)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLatestLifecycleFlow(viewModel.state) {
            if (it.isLoading) {    //loading
                viewBinding.loadingIndicator.visibility = View.VISIBLE
            } else if (it.error != null) {  //error
                viewBinding.loadingIndicator.visibility = View.GONE

                viewBinding.listError.apply {
                    text = it.error
                    visibility = View.VISIBLE
                }
            } else {    //success
                viewBinding.loadingIndicator.visibility = View.GONE
                viewBinding.name.text = args.satelliteName
                viewBinding.date.text = it.data?.date?.let { date -> convertTime(date) }

                //Height/Mass
                viewBinding.height.text = setBoldSpannable(
                    getString(
                        R.string.height_mass,
                        it.data?.height.toString().plus("/").plus(it.data?.mass.toString())
                    )
                )

                //Cost
                viewBinding.cost.text = setBoldSpannable(
                    getString(
                        R.string.cost,
                        DecimalFormat("#,###")
                            .format(it.data?.cost)
                            .replace(",", ".")
                    )
                )

            }
        }

        collectLatestLifecycleFlow(viewModel.position) {
            viewBinding.position.text =
                setBoldSpannable(getString(R.string.last_position, it.posX, it.posY))
        }
    }
}