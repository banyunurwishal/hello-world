package org.d3if4075.jurnal10.ui.wordlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.d3if4075.jurnal10.MainActivity

import org.d3if4075.jurnal10.R
import org.d3if4075.jurnal10.data.Miwok
import org.d3if4075.jurnal10.databinding.FragmentWordListBinding
import org.d3if4075.jurnal10.ui.miwok.MiwokVM
import org.d3if4075.jurnal10.recylceview.RVClickListener
import org.d3if4075.jurnal10.recylceview.WordListAdapter

/**
 * A simple [Fragment] subclass.
 */

@Suppress("SpellCheckingInspection")
class WordListFragment : Fragment(),
    RVClickListener {

    private lateinit var binding: FragmentWordListBinding
    private lateinit var viewModel: MiwokVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)
        val application = activity?.application
        val factory = MiwokVM.Factory(application!!)
        viewModel = ViewModelProvider(this, factory).get(MiwokVM::class.java)
        binding.lifecycleOwner = this
        binding.miwokVm = viewModel

        return binding.root
    }

    private fun title() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = arguments!!.getString("category")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val listData = mutableListOf<Miwok>()
            val category = arguments?.getString("category")

            viewModel.miwok.observe(viewLifecycleOwner, Observer {
                it.map { miwok ->
                    if (miwok.category == category) {
                        listData.add(miwok)
                    }
                }

                val dataFix = listData.distinct()
                val adapter =
                    WordListAdapter(dataFix)
                val rv = binding.rvWordList
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(this.requireContext())
                adapter.listener = this
            })

            viewModel.response.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                }
            })

        }

    }

    override fun onRecyclerViewItemClicked(view: View, miwok: Miwok) {
        super.onRecyclerViewItemClicked(view, miwok)
        Snackbar.make(requireView(), miwok.miwokWord, Snackbar.LENGTH_SHORT).show()
    }


}

