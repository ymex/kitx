package cn.ymex.kitx.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.concurrent.atomic.AtomicInteger

fun <T : View> Fragment.find(@IdRes id: Int): T = view!!.find(id)


fun Fragment.hideInputKeyBoard() {
    context!!.hideInputKeyBoard(view!!)
}

fun Fragment.showInputKeyBoard() {
    context!!.showInputKeyBoard(view!!)
}





class TabHostFragment : Fragment() {
    private val sNextGeneratedId: AtomicInteger =
        AtomicInteger(1)
    private var contextId = generateViewId()
    private val mFragments = mutableListOf<Fragment>()


    companion object {
        private var currentShow = 0
        fun get(fragmentManager: FragmentManager, id: Int): TabHostFragment {
            return fragmentManager.findFragmentById(id)!! as TabHostFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val hostLayout = FrameLayout(requireContext())
        val contextLayout = FrameLayout(requireContext())
        contextLayout.id = contextId
        hostLayout.addView(
            contextLayout,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        return hostLayout
    }


    fun addFragments(vararg fragments: Fragment): TabHostFragment {
        if (fragments.isEmpty()) {
            return this
        }
        if (mFragments.isNotEmpty()) {
            throw IllegalArgumentException("Adding more than once is not allowed")
        }
        val transient = childFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, it ->
            val tf = childFragmentManager.findFragmentByTag(it.javaClass.name + ":$index")
            if (tf != null) {
                mFragments.add(tf)
            } else {
                mFragments.add(it)
                transient.add(contextId, it, it.javaClass.name + ":$index")
            }

        }
        transient.commit()
        return this
    }

    fun show(fragment: Fragment) {
        val transient = childFragmentManager.beginTransaction()
        mFragments.forEach {
            transient.hide(it)
        }
        transient.show(fragment).commit();
    }

    fun autoShow() {
        show(currentShow)
    }


    fun show(index: Int) {
        if (mFragments.size <= index) {
            return
        }
        val fragment = mFragments.get(index)
        val transient = childFragmentManager.beginTransaction()
        mFragments.forEach {
            transient.hide(it)
        }
        transient.show(fragment).commit()
        currentShow = index
        onFragmentShowListener?.onFragmentShow(fragment)
    }


    /**
     * 动态生成id
     */
    fun generateViewId(): Int {
        while (true) {
            val result: Int = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }

    fun getCurrentShowFragment() : Fragment?{
        return getIndexFragment(currentShow)
    }

    fun getCurrentShowFragmentIndex() : Int{
        return currentShow
    }

    fun getIndexFragment(index: Int): Fragment? {
        if (mFragments.size <= index|| index<=0) {
            return null
        }
        return mFragments.get(index)
    }

//    fun setBottomNavigationView(navigationView: BottomNavigationView, vararg actionIds: Int) {
//
//        navigationView.selectedItemId = actionIds[currentShow]
//        navigationView.setOnNavigationItemSelectedListener {
//            var fIndex = -1
//            actionIds.forEachIndexed { index, item ->
//                if (it.itemId == item) {
//                    fIndex = index
//                }
//            }
//            if (fIndex >= 0) {
//                show(fIndex)
//                return@setOnNavigationItemSelectedListener true
//            }
//            return@setOnNavigationItemSelectedListener false
//        }
//    }
//
//
//    fun setBottomNavigationView(
//        navigationView: BottomNavigationView,
//        onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
//    ) {
//        View(requireContext()).visibility
//        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//    }

    var onFragmentShowListener: OnFragmentShowListener? = null


    interface OnFragmentShowListener {
        fun onFragmentShow(fragment: Fragment)
    }
}