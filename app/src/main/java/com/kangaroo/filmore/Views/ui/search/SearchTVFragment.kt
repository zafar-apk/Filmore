package com.kangaroo.filmore.Views.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kangaroo.filmore.R


private const val ARG_PARAM1 = "param1"

class SearchTVFragment : Fragment() {
    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_t_v, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchTVFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}