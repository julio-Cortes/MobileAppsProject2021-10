package com.example.project_01.Views

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Models.CardAdapter
import com.example.project_01.R
import com.example.project_01.ViewModels.MainViewModel
import com.google.android.flexbox.*

class CardsFragment : Fragment() {
    lateinit var recyclerView:RecyclerView
    lateinit var adapter:CardAdapter
    private val viewModel:MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)

        val deck = ArrayList<String>()
        deck.add("1")
        deck.add("2")
        deck.add("3")
        deck.add("4")
        recyclerView = view.findViewById<RecyclerView>(R.id.card_recycler_view)
        adapter = CardAdapter()
        recyclerView.adapter = adapter
        val layoutManager = FlexboxLayoutManager(activity)
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.alignItems = AlignItems.CENTER
        layoutManager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(SpaceItemDecoration(10))
        viewModel.myDeck.observe(viewLifecycleOwner, Observer{
            adapter.update(it)
        })
        return view
    }


}
class SpaceItemDecoration(space:Int) : RecyclerView.ItemDecoration(){
    var halfSpace:Int = space



    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.paddingLeft != halfSpace){
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }
        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
        //super.getItemOffsets(outRect, view, parent, state)
    }


}