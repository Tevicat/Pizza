package com.example.bitsandpizzas


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PizzaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //Помещаем в фрагмент RecyclerView и подгатавливаем массивы для него массив данных
        val pizzaRecycler: RecyclerView =
                inflater.inflate(R.layout.fragment_pizza, container, false) as RecyclerView
        val pizzaNames = Array(Pizza.pizzas.size) { "" }
        for (i in pizzaNames.indices) {
            pizzaNames[i] = Pizza.pizzas[i].name
        }
        //Здесь ошибка - надо получить идентификаторы R.drawable, чтобы подставить их в адаптер
        //Сейчас картинки не подтягиваются
        val pizzaImages = IntArray(Pizza.pizzas.size)
        for (i in pizzaImages.indices) {
            pizzaImages[i] = Pizza.pizzas[i].imageResourceId
        }


        //Создали адаптер и передали ему данные. Соединили адаптер с RecyclerView
        val adapter = CaptionedImagesAdapter(pizzaNames, pizzaImages)
        pizzaRecycler.adapter = adapter
        //LayoutManager - класс, позволяющий выбрать варианты размещения компонентов в RecyclerView.
        val layoutManager = GridLayoutManager(activity, 2)
        pizzaRecycler.layoutManager = layoutManager
        return pizzaRecycler
    }
}
