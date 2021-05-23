package com.example.project_01.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.R
import com.example.project_01.ViewModels.RoomsViewModel
import com.example.project_01.Views.Adapters.LobbyAdapter

class RoomsFragment : Fragment(), OnClickListener {


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LobbyAdapter
    private val viewModel: RoomsViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rooms, container, false)

        adapter = LobbyAdapter(this)
        recyclerView = view.findViewById<RecyclerView>(R.id.room_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)


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
                    viewModel.createLobby(name as String,pass as String)
                    adapter.notifyDataSetChanged()
                }
            }

            val action = RoomsFragmentDirections.actionRoomsFragmentToCreateRoomFragment()
            this.view?.let { Navigation.findNavController(it).navigate(action) }
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
