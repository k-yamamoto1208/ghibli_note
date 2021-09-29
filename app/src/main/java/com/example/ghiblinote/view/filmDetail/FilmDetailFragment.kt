package com.example.ghiblinote.view.filmDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ghiblinote.R
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.data.FilmImage
import com.example.ghiblinote.databinding.FragmentFilmDetailBinding
import com.example.ghiblinote.utils.SingleClick
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FilmDetailFragment : Fragment(), SingleClick {

    private val viewModel: FilmDetailViewModel by viewModels()

    /** ViewBinding用のbindingを定義する */
    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // ローディングGIFのセット
        Glide.with(this).load(R.drawable.loading).into(binding.loadingImage)

        /** viewModel.filmのオブザーバーを定義 */
        val filmObserver = Observer<Film> { film ->
            binding.apply {
                // 映画詳細画像をセット
                filmImage.setImageResource(FilmImage.getDetailImageFromId(film.filmId))
                // お気に入り状態をセット
                if (viewModel.getFavoriteState()) {
                    favoriteButton.setImageResource(R.drawable.favorite_active_image)
                } else {
                    favoriteButton.setImageResource(R.drawable.favorite_default_image)
                }
                // 各テキストをセット
                englishTitleText.text = film.title
                descriptionText.text = film.description
                originalTitleText.text = film.originalTitle
                directorText.text = film.director
                producerText.text = film.producer
                releaseDateText.text = film.releaseDate
                rtScoreText.text = film.rtScore
                // ユーザースコアをセット
                when (viewModel.getScore()) {
                    0 -> setScoreStar0()
                    1 -> setScoreStar1()
                    2 -> setScoreStar2()
                    3 -> setScoreStar3()
                    4 -> setScoreStar4()
                    5 -> setScoreStar5()
                }
                // メモをセット
                if (viewModel.getMemo().isNotEmpty()) memoText.setText(viewModel.getMemo())

                // ローディングを非表示
                loadingLayout.visibility = View.GONE
            }
        }
        // オブザーバーをセット
        viewModel.film.observe(viewLifecycleOwner, filmObserver)

        // TopFragmentから受け取った値がnull出なければ、映画詳細を取得する
        arguments?.getString("film_id")?.let { viewModel.getFilm(it) }

        // お気に入りボタンを押すと活性化 or 非活性化
        binding.favoriteButton.setOnClickListener {
            when (viewModel.getFavoriteState()) {
                true -> {
                    viewModel.setFavoriteState(false)
                    binding.favoriteButton.setImageResource(R.drawable.favorite_default_image)
                }
                else -> {
                    viewModel.setFavoriteState(true)
                    binding.favoriteButton.setImageResource(R.drawable.favorite_active_image)
                }
            }
            // お気に入り状態をDBに保存
            viewModel.saveFavoriteState()
        }

        // "Date"に現在年月日を挿入
        binding.dateText.text = getCurrentDate()

        // 評価の星を押すと活性化 and 非活性化
        binding.star1.setOnClickListener {
            viewModel.setScore(1)
            setScoreStar1()
        }
        binding.star2.setOnClickListener {
            viewModel.setScore(2)
            setScoreStar2()
        }
        binding.star3.setOnClickListener {
            viewModel.setScore(3)
            setScoreStar3()
        }
        binding.star4.setOnClickListener {
            viewModel.setScore(4)
            setScoreStar4()
        }
        binding.star5.setOnClickListener {
            viewModel.setScore(5)
            setScoreStar5()
        }

        // 保存ボタンを押下
        binding.saveButton.setOnSingleClickListener {
            viewModel.setMemo(binding.memoText.text.toString())
            viewModel.saveInfo()
            findNavController().navigate(FilmDetailFragmentDirections.detailToTop())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 現在年月日取得
     *
     * @return "yyyy.MM.dd"形式での年月日
     */
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

    /**
     * ユーザースコアのView更新処理
     */
    private fun setScoreStar0() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_default_image)
            star2.setImageResource(R.drawable.score_star_default_image)
            star3.setImageResource(R.drawable.score_star_default_image)
            star4.setImageResource(R.drawable.score_star_default_image)
            star5.setImageResource(R.drawable.score_star_default_image)
        }
    }

    private fun setScoreStar1() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_active_image)
            star2.setImageResource(R.drawable.score_star_default_image)
            star3.setImageResource(R.drawable.score_star_default_image)
            star4.setImageResource(R.drawable.score_star_default_image)
            star5.setImageResource(R.drawable.score_star_default_image)
        }
    }

    private fun setScoreStar2() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_active_image)
            star2.setImageResource(R.drawable.score_star_active_image)
            star3.setImageResource(R.drawable.score_star_default_image)
            star4.setImageResource(R.drawable.score_star_default_image)
            star5.setImageResource(R.drawable.score_star_default_image)
        }
    }

    private fun setScoreStar3() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_active_image)
            star2.setImageResource(R.drawable.score_star_active_image)
            star3.setImageResource(R.drawable.score_star_active_image)
            star4.setImageResource(R.drawable.score_star_default_image)
            star5.setImageResource(R.drawable.score_star_default_image)
        }
    }

    private fun setScoreStar4() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_active_image)
            star2.setImageResource(R.drawable.score_star_active_image)
            star3.setImageResource(R.drawable.score_star_active_image)
            star4.setImageResource(R.drawable.score_star_active_image)
            star5.setImageResource(R.drawable.score_star_default_image)
        }
    }

    private fun setScoreStar5() {
        binding.apply {
            star1.setImageResource(R.drawable.score_star_active_image)
            star2.setImageResource(R.drawable.score_star_active_image)
            star3.setImageResource(R.drawable.score_star_active_image)
            star4.setImageResource(R.drawable.score_star_active_image)
            star5.setImageResource(R.drawable.score_star_active_image)
        }
    }
}