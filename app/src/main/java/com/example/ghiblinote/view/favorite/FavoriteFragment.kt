package com.example.ghiblinote.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ghiblinote.data.Film
import com.example.ghiblinote.databinding.FragmentFavoriteBinding
import com.example.ghiblinote.view.note.FavoriteAndMemoListAdapter
import com.example.ghiblinote.view.note.MemoListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    /** ViewBinding用のbindingを定義する */
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private var favoriteListAdapter = FavoriteAndMemoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerViewをセット
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteListAdapter
        }
        // RecyclerViewのクリックリスナー設定
        favoriteListAdapter.setOnItemClickListener(object :
            FavoriteAndMemoListAdapter.OnItemClickListener {
            override fun onItemClickListener(filmId: String) {
                val action = FavoriteFragmentDirections.favoriteListToDetail(filmId)
                findNavController().navigate(action)
            }
        })

        /** viewModel.favoriteFilmListのオブザーバーを定義 */
        val favoriteListObserver = Observer<List<Film>> {
            // 取得したリストをアダプターにセット
            favoriteListAdapter.setFilmList(it)
            // 取得したリストが空のときは「結果なし」のレイアウトを表示。空でない時は、非表示にする
            if (it.isEmpty()) {
                binding.noResultsLayout.visibility = View.VISIBLE
            } else {
                binding.noResultsLayout.visibility = View.GONE
            }
        }

        // オブザーバーをセット
        viewModel.favoriteFilmList.observe(viewLifecycleOwner, favoriteListObserver)

        // お気に入り映画一覧をDBから取得
        viewModel.getFavoriteFilmList()
    }

    override fun onStop() {
        super.onStop()

        // 画面離脱時にお気に入り状態を更新
        viewModel.updateFavoriteStateFromList(
            favoriteListAdapter.getFavoriteUpdateList(),
            favoriteListAdapter.getNonFavoriteUpdateList()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}