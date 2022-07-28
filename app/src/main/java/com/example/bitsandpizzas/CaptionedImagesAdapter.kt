package com.example.bitsandpizzas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

//RecyclerView - компонент для отображения элементов списка. Элементы списка представлены в виде объектов viewHolder
class CaptionedImagesAdapter(private var captions: Array<String>, private var imageId: IntArray)
// Адаптер RecyclerView с указанием класса, который будет хранить ссылки на виджеты элемента списка.
    : RecyclerView.Adapter<CaptionedImagesAdapter.MyViewHolder>() {
    //Создаем листенер для компонентов ресайклер вью - метод определяет с какого экрана был вызов
    private lateinit var listener: Listener
    interface Listener {
        fun onClick(initializer: Int)
    }
    // Устанавливаем значение лисенера
    fun setListener(listener: Listener) {
        this.listener = listener
    }

    //В Kotlin вложенный класс без модификаторов является аналогом статического вложенного класса в Java
    // Вутренний класс ViewHolder -контейнер для всех компонентов списка
    // RecyclerView создаёт ровно столько контейнеров, сколько нужно для отображения на экране.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val imageView: ImageView = itemView.findViewById(R.id.info_image)
        val textView: TextView = itemView.findViewById((R.id.info_text))

    }
    // Возвращает количество элементов списка.
    override fun getItemCount() = captions.size

    //onCreateViewHolder() - данный метод вызывается, чтобы создать объекты viewHolder
    // и передать им макет, по которому будут отображаться элементы списка.
    // parent-группа вью, в которую будет добавлен вью холдер
    // viewType - это произвольное цифровое значение от 0 и выше, которое необходимо для того, чтобы различать объекты viewHolder между собой.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Накачали вьюхолдер нашей карточкой - .from это статический метод, который создает экземпляр LayoutInflater с заданным контекстом
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_captioned_image, parent, false)
        return MyViewHolder(itemView)
    }

    //onBindViewHolder() -привязываем данные к объекту viewHolder
    //Параметр position отвечает за позицию в списке (индекс), по которой можно получить нужные данные.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageResource(imageId[position])
        holder.textView.text = captions[position]
      /*(  holder.cardView.setOnClickListener(object: View.OnClickListener {
            override fun onClick (view: View){
            }
            }))*/

        }
    }




