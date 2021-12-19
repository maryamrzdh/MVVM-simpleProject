package com.example.alibabatask.ui.fragment.second

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.alibabatask.R
import com.example.alibabatask.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    val myCalendar: Calendar = Calendar.getInstance()

    private val args by navArgs<SecondFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_second,container,false)
        binding.edtDest.setText(args.destination)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateDateFormat()
            }

        binding.chooseDate.setOnClickListener {
            DatePickerDialog(
                requireContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSearch.setOnClickListener {
            back()
        }

        binding.include.btnBack.setOnClickListener {
            back()
        }

        binding.edtFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkButton()
            }
        })


        binding.edtDest.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkButton()
            }
        })
    }

    fun checkButton(){
        binding.btnSearch.isEnabled=(binding.edtDest.text.isNotEmpty()&&binding.edtFrom.text.isNotEmpty()&&binding.tvDate.text.isNotEmpty())
    }
    private fun updateDateFormat() {
        val myFormat = "dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDate.text = sdf.format(myCalendar.time)
        checkButton()
    }

    private fun back(){
        val result = Bundle().apply {
            putString("data", binding.edtDest.text.toString())
        }
        requireActivity().supportFragmentManager.setFragmentResult("REQUEST_KEY", result)
        findNavController().navigateUp()
    }
}
