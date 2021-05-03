package com.example.project_01.Views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.R
import com.example.project_01.ViewModels.RoomsViewModel
import com.example.project_01.Views.Adapters.RoomAdapter

class RoomsFragment : Fragment(), OnClickListener {


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RoomAdapter
    private val viewModel: RoomsViewModel by activityViewModels()
    var name: String? = ""
    var password: String? = ""


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rooms, container, false)

        adapter = RoomAdapter(this)
        recyclerView = view.findViewById<RecyclerView>(R.id.room_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.myCases.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })

        val button = view.findViewById<Button>(R.id.buttonCreateR)
        button.setOnClickListener {
            /*
            val fragmentTwo = CreateRoomFragment()
            activity?.supportFragmentManager?.commit {
                this.replace(R.id.main_nav_host,fragmentTwo)
                this.addToBackStack(null)
            }

             */
            val action = RoomsFragmentDirections.actionRoomsFragmentToCreateRoomFragment()
            this.view?.let { Navigation.findNavController(it).navigate(action) };
        }
        name = "nombre - parametro"
        password= "parametros"
        viewModel.addCase(name,password)
        return view
    }

    override fun onClickItem(item: Any) {
        //viewModel.deleteCase()
    }
}
