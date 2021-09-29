package com.example.ghiblinote.view.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghiblinote.R
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.data.FilmImage
import com.example.ghiblinote.databinding.FilmListItemBinding
import com.example.ghiblinote.utils.SingleClick

class TopFilmListAdapter :
    RecyclerView.Adapter<TopFilmListAdapter.ViewHolder>(), SingleClick {

    /** 映画一覧 */
    private val filmList = mutableListOf<Film>()

    /**
     * 映画一覧をアダプターにセットする
     *
     * @param item 映画一覧
     */
    fun setFilmList(item: List<Film>) {
        filmList.clear()
        filmList.addAll(item)
        notifyDataSetChanged()
    }

    /** クリックリスナー格納変数 */
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])

        // アイテムをクリック
        holder.binding.parentLayout.setOnSingleClickListener {
            listener.onItemClickListener(filmList[position].filmId)
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(val binding: FilmListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.apply {
                // 映画IDから該当画像を取得し、レイアウトに挿入
                val image = FilmImage.getIconImageFromId(film.filmId)
                filmImage.setImageResource(image)
                // 取得した各テキストをレイアウトに挿入
                englishTitleText.text = film.title
                originalTitleText.text = film.originalTitle
                descriptionText.text = film.description
                // 「お気に入り済」「メモ済」を判断し、Visibility制御
                if (film.isFavorite == null || film.isFavorite == false)
                    favoriteIconImage.visibility = View.GONE
                if (film.memo.isNullOrEmpty()) memoIconImage.visibility = View.GONE
            }
        }
    }

    /**
     * クリックイベントインターフェース
     */
    interface OnItemClickListener {
        fun onItemClickListener(filmId: String)
    }

    /**
     * クリックリスナーをセット
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}