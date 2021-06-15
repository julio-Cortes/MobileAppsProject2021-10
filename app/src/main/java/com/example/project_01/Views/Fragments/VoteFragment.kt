package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.Models.Lobby
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.Views.Adapters.CardAdapter
import org.koin.android.ext.android.inject


class VoteFragment : Fragment(), OnClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CardAdapter
    private val viewModel: LobbyViewModel by inject()
    val args : VoteFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.card_recycler_view)
        adapter = CardAdapter(this)
        recyclerView.adapter = adapter
        adapter.data = args.deck.cards
        val layoutManager = GridLayoutManager(context, 6)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val item = adapter.itemCount
                val extra: Int
                extra = item % 3
                if (extra == 0) {
                    return 2
                }
                if (item - (position + 1) < extra) {
                    return (6  / extra) as Int

                } else {
                    return 2
                }

            }
        }
        recyclerView.layoutManager = layoutManager

        return view
    }

    override fun onClickItem(num: Any) {
        if (num is String){
            view?.let { viewModel.vote(num, it) }

        }
    }


}