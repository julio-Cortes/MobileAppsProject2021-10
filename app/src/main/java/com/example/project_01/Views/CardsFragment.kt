package com.example.project_01.Views

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.Models.CardAdapter
import com.example.project_01.R
import com.example.project_01.ViewModels.MainViewModel
import com.google.android.flexbox.*

class CardsFragment : Fragment(), OnClickListener {
    lateinit var recyclerView:RecyclerView
    lateinit var adapter:CardAdapter
    private val viewModel:MainViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)

        val deck = ArrayList<String>()
        deck.add("1")
        deck.add("2")
        deck.add("3")
        deck.add("4")
        recyclerView = view.findViewById<RecyclerView>(R.id.card_recycler_view)
        adapter = CardAdapter(this)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(context, 6)


        //val totalSize: Int = customList.size()

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


        viewModel.myDeck.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        return view
    }

    override fun onClickItem(num: Any) {
        val action = CardsFragmentDirections.actionCardsFragmentToSelectedCard(num as String)
        this.view?.let { Navigation.findNavController(it).navigate(action) };
    }


}

