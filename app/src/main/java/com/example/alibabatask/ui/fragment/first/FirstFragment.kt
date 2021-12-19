package com.example.alibabatask.ui.fragment.first

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alibabatask.R
import com.example.alibabatask.databinding.FragmentFirstBinding
import com.example.alibabatask.model.Datum
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.FragmentResultListener




@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstViewModel
    private lateinit var binding:FragmentFirstBinding
    private lateinit var mAdapter:MyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_first,container,false)
        viewModel= ViewModelProviders.of(this).get(FirstViewModel::class.java)
        binding.progressBar.visibility=View.VISIBLE
        viewModel.getData()
        observe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.setFragmentResultListener("REQUEST_KEY",
            viewLifecycleOwner, { requestKey: String, bundle: Bundle ->

                binding.edtDestination.setText(bundle.getString("data"))


            })

        binding.edtDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.btnDestination.isEnabled=s.toString().isNotEmpty()
            }
        })

        binding.btnDestination.setOnClickListener {
            val direction=FirstFragmentDirections.actionFirstFragmentToSecondFragment(binding.edtDestination.text.toString())
            findNavController().navigate(direction)
            binding.edtDestination.text.clear()
        }
    }

    private fun initAdapter(list:MutableList<Datum>){
        mAdapter= MyListAdapter(requireActivity(),list)
        binding.recyclerUser.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter=mAdapter
        }
    }

    private fun observe(){
        viewModel.getModel().observe(requireActivity(),{
            binding.progressBar.visibility=View.GONE
            if (it!=null){
                initAdapter(it)
            }
            else
                binding.vf.displayedChild=1
        })
    }
}