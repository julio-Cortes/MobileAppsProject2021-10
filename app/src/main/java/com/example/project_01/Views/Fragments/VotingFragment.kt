package com.example.project_01.Views.Fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.R
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.Views.Adapters.VotingAdapter
import org.koin.android.ext.android.inject


class VotingFragment : Fragment(), OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: VotingAdapter
    private val viewModel: LobbyViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voting, container, false)
        adapter = VotingAdapter()
        recyclerView = view.findViewById<RecyclerView>(R.id.room_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.getResults()

        viewModel.members.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            )



        return view
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroyView() {
        viewModel.nullRoom()
        super.onDestroyView()

    }

    override fun onClickItem(item: Any) {
        //
    }

}