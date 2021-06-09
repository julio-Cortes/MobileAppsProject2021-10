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
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.R
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel
import com.example.project_01.Views.Adapters.LobbyAdapter

class LobbysFragment : Fragment(), OnClickListener{


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LobbyAdapter
    private val viewModel: LobbyViewModel by activityViewModels()
    private val userviewModel: UsersViewModel by activityViewModels()


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

        //Settingup Navigator
        viewModel.setNavigator(activity as MainActivity)

        viewModel.MyRooms.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })

        val button = view.findViewById<Button>(R.id.button_create)

        button.setOnClickListener {
            setFragmentResultListener("REQUEST_ROOM"){ requestKey: String, bundle: Bundle ->
                if (requestKey == "REQUEST_ROOM") {
                    val name = bundle.get("name")
                    val pass = bundle.get("pass")
                    val deck = bundle.get("deck")
                    viewModel.createLobby(userviewModel.userToken, name as String,pass as String, deck as Deck)
                    adapter.notifyDataSetChanged()
                }
            }
            viewModel.LobbyFragmentToCreateLobbyFragment(view)

        }
        return view
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteLobby(adapter.data[viewHolder.adapterPosition].id)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClickItem(item: Any) {
        //viewModel.deleteCase()
    }


}
