package com.example.ghiblinote.view.note

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
import com.example.ghiblinote.databinding.FragmentMemoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoListFragment : Fragment() {

    private val viewModel: MemoListViewModel by viewModels()

    /** ViewBinding用のbindingを定義する */
    private var _binding: FragmentMemoListBinding? = null
    private val binding get() = _binding!!

    private val memoListAdapter = FavoriteAndMemoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerViewをセット
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = memoListAdapter
        }
        // RecyclerViewのクリックリスナー設定
        memoListAdapter.setOnItemClickListener(object :
            FavoriteAndMemoListAdapter.OnItemClickListener {
            override fun onItemClickListener(filmId: String) {
                val action = MemoListFragmentDirections.memoListToDetail(filmId)
                findNavController().navigate(action)
            }
        })

        /** viewModel.filmListのオブザーバーを定義 */
        val filmListObserver = Observer<List<Film>> {
            // 取得したリストをアダプターにセット
            memoListAdapter.setFilmList(it)
            // 取得したリストが空のときは「結果なし」のレイアウトを表示。空でない時は、非表示にする
            if (it.isEmpty()) {
                binding.noResultsLayout.visibility = View.VISIBLE
            } else {
                binding.noResultsLayout.visibility = View.GONE
            }
        }
        // オブザーバーをセット
        viewModel.filmList.observe(viewLifecycleOwner, filmListObserver)

        // メモした映画一覧をDBから取得
        viewModel.getMemoList()
    }

    override fun onStop() {
        super.onStop()

        // 画面離脱時にお気に入り状態を更新
        viewModel.updateFavoriteStateFromList(
            memoListAdapter.getFavoriteUpdateList(),
            memoListAdapter.getNonFavoriteUpdateList()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}