package com.example.bitsandpizzas

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class OrderActivity : AppCompatActivity() {
    //Графические элементы приложения
    private lateinit var toolbar: Toolbar
    private lateinit var actionbar: ActionBar
    private lateinit var totalAmount: EditText
    private lateinit var peopleNumber: EditText
    private lateinit var otherTip: EditText
    private lateinit var tipRadioGroup: RadioGroup
    private lateinit var btnReset: Button
    private lateinit var btnCalculate: Button
    private lateinit var tipAmount: TextView
    private lateinit var totalPay: TextView
    private lateinit var personTip: TextView
    var radioCheckedId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionbar = supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)

        //Устанавливаем доступ к графическим элементам
        totalAmount = findViewById(R.id.total_amount_value)
        //Ставим фокус на поле ввода общей суммы счета
        totalAmount.requestFocus()
        peopleNumber = findViewById(R.id.num_people_value)
        otherTip = findViewById(R.id.other_tip_value)
        //Блокируем поле ввода дрвугих чаевых, так как радио батн стоит по умолчанию в начале на 1 варианте процентов
        otherTip.isEnabled = false
        tipRadioGroup = findViewById(R.id.radio_group_tips)
        btnCalculate = findViewById(R.id.btn_calculate)
        //Блокируем кнопку так как в начале нет вводных данныхх для расчета
        btnCalculate.isEnabled = false
        btnReset = findViewById(R.id.btn_reset)
        tipAmount = findViewById(R.id.tip_amount_value)
        totalPay = findViewById(R.id.total_pay_value)
        personTip = findViewById(R.id.tip_person_value)

        //Устанавливаем листенер, чтобы знать какой радио батн выбрал
        //Устанавливаем листенеры на кнопки
        peopleNumber.setOnKeyListener(MyEditTextListener())
        totalAmount.setOnKeyListener(MyEditTextListener())
        otherTip.setOnKeyListener(MyEditTextListener())
        tipRadioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                //Если выбран процент из предложенных - проверяем заполненность  полей людей и суммы
                if (checkedId == R.id.tip_percent_1 || checkedId == R.id.tip_percent_2) {
                    otherTip.isEnabled = false
                }
                if (totalAmount.text.isNotEmpty() && peopleNumber.text.isNotEmpty()) {
                    btnCalculate.isEnabled = true
                }
                //Если выбран другой процент - даем его ввести в поле и проверяем заполненность  полей людей и суммы
                // для активации поля button
                if (checkedId == R.id.tip_percent_other) {
                    otherTip.isEnabled = true
                    otherTip.requestFocus()
                    if (totalAmount.text.isNotEmpty() && peopleNumber.text.isNotEmpty() && otherTip.text.isNotEmpty()) {
                        btnCalculate.isEnabled = true
                    }
                }
                radioCheckedId = checkedId
            }
        })
    }

    // Реализуем поведение листенера для текстовых поллей ввода суммы, процентов и количества людей

    inner class MyEditTextListener : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            when (v!!.id) {
                R.id.total_amount_value, R.id.num_people_value -> if (totalAmount.text.isNotEmpty() && peopleNumber.text.isNotEmpty()) {
                    btnCalculate.isEnabled = true
                }
                R.id.other_tip_value -> if (totalAmount.text.isNotEmpty() && peopleNumber.text.isNotEmpty() && otherTip.text.isNotEmpty()) {
                    btnCalculate.isEnabled = true
                }
            }
            return false
        }
    }

    //Обрабатываем кнопки RESET, CALCULATED
    fun onMyButtonClick(view: View) {
        when (view) {
            //Подчищаем все значения , сбрасываем радио батн и устанавливаем ее значение по умолчанию, фокус на первое поле
            btnReset -> {
                totalAmount.setText("")
                totalAmount.requestFocus()
                peopleNumber.setText("")
                otherTip.setText("")
                tipRadioGroup.clearCheck()
                tipRadioGroup.check(R.id.tip_percent_1)
                tipAmount.text = ""
                totalPay.text = ""
                personTip.text = ""
            }

            btnCalculate -> {
                //Переводим введенные значения в double
                val billAmount = totalAmount.text.toString().toDouble()
                val totalPeople = peopleNumber.text.toString().toDouble()
                var percentage = 0.15
                var isError = false
                //Проверяем введенные значения на 0 - если нулевые - выкидываем ошибку
                if (billAmount < 1) {

                    isError = true
                }
                if (totalPeople < 1) {
                    isError = true
                }
                //На всякий случай проверяем, что выбран RadioButton
                when (radioCheckedId) {
                    R.id.tip_percent_1 -> {
                        percentage = resources.getString(R.string.tip_percent_title_1)
                                             .replace("%", "", false).toDouble() / 100
                    }
                    R.id.tip_percent_2 -> {
                        percentage = resources.getString(R.string.tip_percent_title_2)
                                             .replace("%", "", false).toDouble() / 100
                    }
                    R.id.tip_percent_other -> {
                        percentage = otherTip.text.toString().toDouble() / 100
                    }
                }
                if (!isError) {
                    val calculatedTip = billAmount * percentage
                    tipAmount.text = calculatedTip.toString()
                    val calculatedPay = billAmount + calculatedTip
                    totalPay.text = calculatedPay.toString()
                    personTip.text = calculatedPay.div(totalPeople).toString()
                }

            }
        }
    }

    //Обработка фаб-кнопки - по нажатию появляется снекбар
    public fun onClickDone(view: View) {
        val text: CharSequence = "Your order has been updated"
        val duration = Snackbar.LENGTH_SHORT
        //создаем снекбар и привязываем ко вьюхе CoordinatorLayout(id.coordinator)
        val snackbar: Snackbar = Snackbar.make(findViewById(R.id.coordinator), text, duration)
        snackbar.setAction("Undo", object : View.OnClickListener {
            override public fun onClick(view: View) {
                val toast: Toast = Toast.makeText(this@OrderActivity, "Undone!", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
        snackbar.show()
    }
}





