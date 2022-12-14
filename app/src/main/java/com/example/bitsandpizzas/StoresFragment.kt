package com.example.bitsandpizzas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

//Класс - списковый фрагмент с пиццами
class StoresFragment : ListFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //Создаем адаптер для списка магазинов
        val storesAdapter = ArrayAdapter(inflater.context,
                                         android.R.layout.simple_list_item_1,
                                         resources.getStringArray(R.array.stores))
        listAdapter = storesAdapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}