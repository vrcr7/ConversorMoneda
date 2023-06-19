package com.example.conversormodena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.conversormodena.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var convert:Button
    lateinit var  reset:Button
    lateinit var  total:TextView
    lateinit var eudol:String
    lateinit var monto:EditText
    lateinit var spinner: Spinner
    lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        convert = binding.convert
        reset= binding.reset
        total = binding.total
        monto = binding.saldo

        spinner = binding.spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this,R.array.divisa,
            android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter

            //controlamos por accion si es dolar o euro
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    var selectedValue = parent.getItemAtPosition(position) as String
                    eudol = selectedValue
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Acciones a realizar cuando no se ha seleccionado nada en el Spinner (opcional)
                }
            }

            //Accion para que imprima el total dolar o euro
            convert.setOnClickListener(View.OnClickListener { v: View ->
                if (monto.text.toString().isNotEmpty()) {
                    try {
                        if (eudol == "DOLAR") {
                            var montoNumerico = monto.text.toString().toDouble()
                            montoNumerico *= 800
                            total.text = "$montoNumerico pesos en Dolares"
                        }
                        if (eudol == "EURO") {
                            var montoNumerico = monto.text.toString().toDouble()
                            montoNumerico *= 900
                            total.text = "$montoNumerico pesos en Euros"
                        }
                    } catch (e: NumberFormatException) {
                        total.text = "Error de conversión"
                    }
                } else {
                    total.text = "Debe ingresar un valor"
                }
            })

            //limpiar campos
            reset.setOnClickListener(View.OnClickListener { v: View ->
                monto.setText("0")
                total.text = ""

                try {
                    val montoNumerico = monto.text.toString().toDouble()
                    total.text = montoNumerico.toString()
                } catch (e: NumberFormatException) {
                    total.text = "0"
                }
            })


        }
    }
}