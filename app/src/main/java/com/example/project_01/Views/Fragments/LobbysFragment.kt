package com.example.project_01.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.R
import com.example.project_01.Repositories.GeneralRepository
import com.example.project_01.Repositories.UserRepository
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel
import com.example.project_01.Views.Adapters.LobbyAdapter
import org.koin.android.ext.android.inject

class LobbysFragment : Fragment(), OnClickListener{


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LobbyAdapter
    private val viewModel: LobbyViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rooms, container, false)

        //RecylclerView Setup
        adapter = LobbyAdapter(this)
        recyclerView = view.findViewById<RecyclerView>(R.id.room_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        //Use to delete swiping right
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)


        viewModel.MyRooms.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })

        val button = view.findViewById<Button>(R.id.button_create)

        button.setOnClickListener {
            setFragmentResultListener("REQUEST_ROOM"){ requestKey: String, bundle: Bundle ->
                if (requestKey == "REQUEST_ROOM") {
                    val name = bundle.get("name")
                    val pass = bundle.get("pass")
                    val deck_name = bundle.get("name_deck").toString()
                    val cards_deck = bundle.get("cards_deck") as MutableList<String>
                    viewModel.createLobby(name as String,pass as String, deck_name, cards_deck)
                    adapter.notifyDataSetChanged()
                }
            }
            viewModel.LobbyFragmentToCreateLobbyFragment(view)
        }

        val button_join = view.findViewById<Button>(R.id.button_join)

        button_join.setOnClickListener {
            setFragmentResultListener("REQUEST_ROOM_JOIN"){ requestKey: String, bundle: Bundle ->
                if (requestKey == "REQUEST_ROOM_JOIN") {
                    val name = bundle.get("name")
                    val pass = bundle.get("pass")
                    viewModel.joinLobby(name as String,pass as String)
                    adapter.notifyDataSetChanged()
                }
            }
            viewModel.LobbyFragmentToJoinLobbyFragment(view)
        }
        return view
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteLobby(adapter.data[viewHolder.adapterPosition].id, adapter.data[viewHolder.absoluteAdapterPosition].name)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClickItem(item: Any) {
        if (item is Lobby){
            view?.let { viewModel.LobbyFragmentToVoteFragment(it, item) }
        }
    }


}
