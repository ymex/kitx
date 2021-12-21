package cn.ymex.kitx.sample.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import cn.ymex.kitx.core.adapter.recycler.DelegateAdapter

import cn.ymex.kitx.sample.adapter.BinderItemCata
import cn.ymex.kitx.sample.adapter.BinderItemVideo
import cn.ymex.kitx.sample.adapter.Video
import cn.ymex.kitx.sample.databinding.MainFragmentBinding
import cn.ymex.kitx.start.app.ViewBindingFragment
import cn.ymex.kitx.snippet.view.itemDecorationDrawable
import cn.ymex.kitx.snippet.view.verticalItemDecoration

class MainFragment : ViewBindingFragment<MainFragmentBinding>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var viewModel: MainViewModel


    override fun viewBinding(): MainFragmentBinding {
        return MainFragmentBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val delegateAdapter = DelegateAdapter.create()
//        val layoutManager = LinearLayoutManager(context!!,LinearLayoutManager.VERTICAL,false)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (delegateAdapter.getItem(position) is Video) {
                    return 1
                } else {
                    return layoutManager.spanCount
                }
            }
        }



        vb.vRecycler.layoutManager = layoutManager

        vb.vRecycler.addItemDecoration(
            verticalItemDecoration(
                requireContext(),
                itemDecorationDrawable(Color.GREEN, 20, 20, 8)
            )
        )




        delegateAdapter.bind(String::class.java, BinderItemCata())
            .bind(Video::class.java, BinderItemVideo())
        delegateAdapter.attachRecyclerView(vb.vRecycler)

        delegateAdapter.data = mutableListOf(
            "热门电影",
            Video("银翼杀手2049"), Video("缝纫机乐队"), Video("常在你左右"), Video("追龙"),
            "动作电影",
            Video("雷神3：诸神黄昏"), Video("羞羞的铁拳"), Video("十八洞村"), Video("王牌特工2：黄金圈"),
            "喜剧电影",
            Video("全球风暴"), Video("天才枪手"), Video("我的爸爸是森林之王")
        )
    }

}
