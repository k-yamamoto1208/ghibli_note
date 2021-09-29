package com.example.ghiblinote.view.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghiblinote.R
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.data.FilmImage
import com.example.ghiblinote.databinding.FavoriteMemoListItemBinding
import com.example.ghiblinote.utils.SingleClick

class FavoriteAndMemoListAdapter : RecyclerView.Adapter<FavoriteAndMemoListAdapter.ViewHolder>(), SingleClick {

    /** メモした映画一覧 */
    private val filmList = mutableListOf<Film>()

    /** クリックリスナー格納変数 */
    lateinit var listener: OnItemClickListener

    /**
     * メモした映画一覧をアダプターにセットする
     *
     * @param item 映画一覧
     */
    fun setFilmList(item: List<Film>) {
        filmList.clear()
        filmList.addAll(item)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: FavoriteMemoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.apply {
                filmImage.setImageResource(FilmImage.getIconImageFromId(film.filmId))
                englishTitleText.text = film.title
                originalTitleText.text = film.originalTitle
                descriptionText.text = film.description
                if (film.isFavorite != null && film.isFavorite == true) {
                    favoriteIconImage.setImageResource(R.drawable.memo_list_favorite_active_icon)
                } else {
                    favoriteIconImage.setImageResource(R.drawable.memo_list_favorite_default_icon)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            FavoriteMemoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])

        // カセットをクリック
        holder.binding.parentLayout.setOnSingleClickListener {
            listener.onItemClickListener(filmList[position].filmId)
        }

        // ハート（お気に入り）をクリック
        holder.binding.favoriteIconImage.setOnSingleClickListener {
            if (filmList[position].isFavorite == true) {
                holder.binding.favoriteIconImage.setImageResource(R.drawable.memo_list_favorite_default_icon)
                filmList[position].isFavorite = false
            } else {
                holder.binding.favoriteIconImage.setImageResource(R.drawable.memo_list_favorite_active_icon)
                filmList[position].isFavorite = true
            }
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

    /**
     * お気に入り登録されている映画IDのリストを取得
     *
     * @return お気に入り登録されている映画IDのリスト
     */
    fun getFavoriteUpdateList(): List<String> {
        return filmList.filter { it.isFavorite == true }.map { it.filmId }
    }

    /**
     * お気に入り解除されている映画IDのリストを取得
     *
     * @return お気に入り解除されている映画IDのリスト
     */
    fun getNonFavoriteUpdateList(): List<String> {
        return filmList.filter { it.isFavorite == false }.map { it.filmId }
    }

    /**
     * クリックイベントインターフェース
     */
    interface OnItemClickListener {

        /**
         * カセットクリックリスナー
         *
         * @param filmId クリックされた映画ID
         */
        fun onItemClickListener(filmId: String)
    }

    /**
     * クリックリスナーをセット
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}