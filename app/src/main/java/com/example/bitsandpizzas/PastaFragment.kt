package com.example.bitsandpizzas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class PastaFragment : ListFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//Создаем адаптер для списка паст
        val pastaAdapter = ArrayAdapter<String>(inflater.context,
                                                android.R.layout.simple_list_item_1,
                                                resources.getStringArray(R.array.pasta))
        listAdapter = pastaAdapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}