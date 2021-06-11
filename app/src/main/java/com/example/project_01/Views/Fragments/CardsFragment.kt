package com.example.project_01.Views.Fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.MainActivity
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.Views.Adapters.CardAdapter
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.util.*


class CardsFragment : Fragment(), OnClickListener {
    lateinit var recyclerView:RecyclerView
    lateinit var adapter: CardAdapter
    private val viewModel:CardsViewModel by inject()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)


        recyclerView = view.findViewById<RecyclerView>(R.id.card_recycler_view)
        adapter = CardAdapter(this)
        recyclerView.adapter = adapter
        viewModel.currentDeck.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            adapter.update(it.cards)
        })

        val layoutManager = GridLayoutManager(context, 6)

        viewModel.Decks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.deckRepository.deck_credentials = it
        })

        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
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
        view?.let { viewModel.CardFragmentToSelectedCard(it, num as String) }

    }


}

