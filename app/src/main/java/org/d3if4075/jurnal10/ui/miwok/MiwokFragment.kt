package org.d3if4075.jurnal10.ui.miwok

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.d3if4075.jurnal10.MainActivity

import org.d3if4075.jurnal10.R
import org.d3if4075.jurnal10.data.Miwok
import org.d3if4075.jurnal10.databinding.FragmentMiwokBinding
import org.d3if4075.jurnal10.utils.RVClickListener

/**
 * A simple [Fragment] subclass.
 */
@Suppress("SpellCheckingInspection")
class MiwokFragment : Fragment(),
    RVClickListener {

    private lateinit var binding: FragmentMiwokBinding
    private lateinit var viewModel: MiwokVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miwok, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun title() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "6706184075"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = activity?.application
        val factory = MiwokVM.Factory(application!!)
        viewModel = ViewModelProviders.of(this, factory).get(MiwokVM::class.java)
        binding.miwokVm = viewModel

        viewModel.miwok.observe(viewLifecycleOwner, Observer {
            val dataFix = it.distinctBy { miwok -> miwok.category }
            val adapter = MiwokAdapter(dataFix)
            val rv = binding.rvMiwok
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

    override fun onRecyclerViewItemClicked(view: View, miwok: Miwok) {
        super.onRecyclerViewItemClicked(view, miwok)
        val bundle = Bundle()
        bundle.putString("category", miwok.category)
        view.findNavController().navigate(R.id.action_miwokFragment_to_wordListFragment, bundle)
    }
}
