package org.d3if4075.jurnal10.ui.wordlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if4075.jurnal10.R
import org.d3if4075.jurnal10.data.Miwok
import org.d3if4075.jurnal10.databinding.RvWordlistBinding
import org.d3if4075.jurnal10.utils.RVClickListener

@Suppress("SpellCheckingInspection")
class WordListAdapter(
    private val miwok: List<Miwok>
) : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    var listener: RVClickListener? = null

    inner class WordListViewHolder(
        val recyclerviewWordlistBinding: RvWordlistBinding
    ) : RecyclerView.ViewHolder(recyclerviewWordlistBinding.root)

    override fun getItemCount() = miwok.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_wordlist, parent, false)
    )

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        holder.recyclerviewWordlistBinding.listWordList.setBackgroundColor(Color.parseColor(miwok[position].background))
        holder.recyclerviewWordlistBinding.tvInggris.text = miwok[position].defaultWord
        holder.recyclerviewWordlistBinding.tvMiwok.text = miwok[position].miwokWord

        if (miwok[position].image == "") {
            Glide.with(holder.itemView.context).clear(holder.recyclerviewWordlistBinding.image)
            holder.recyclerviewWordlistBinding.image.setImageDrawable(null)
            holder.recyclerviewWordlistBinding.image.visibility = View.GONE
        } else {
            Glide.with(holder.itemView.context)
                .load("http://dif.indraazimi.com/miwok/${miwok[position].image}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .dontAnimate()
                .into(holder.recyclerviewWordlistBinding.image)
        }

        holder.recyclerviewWordlistBinding.listWordList.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, miwok[position])
        }
    }
}