package com.example.bitsandpizzas

class Pizza private constructor( val name:String,val imageResourceId: Int)  {
    companion object {
        public val pizzas: Array<Pizza> = arrayOf(
            Pizza("Diavolo", R.drawable.diavolo),
            Pizza("Funghi", R.drawable.funghi))
        }
    override fun toString() : String = this.name
    }