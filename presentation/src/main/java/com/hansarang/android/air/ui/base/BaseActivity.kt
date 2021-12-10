package com.hansarang.android.air.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hansarang.android.air.BR
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityMainBinding
import java.lang.reflect.ParameterizedType
import java.util.*

abstract class BaseActivity<VB: ViewDataBinding, VM: ViewModel>: AppCompatActivity() {
    protected lateinit var binding: VB
    private lateinit var mViewModel: VM

    protected abstract val viewModel: VM

    protected abstract fun observerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
        observerViewModel()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutRes())
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
        binding.setVariable(BR.vm, mViewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }

    @LayoutRes
    private fun layoutRes(): Int {
        val split =
            ((Objects.requireNonNull(javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<*>)
                .simpleName.replace("Binding$".toRegex(), "")
                .split("(?<=.)(?=\\p{Upper})".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()

        val name = StringBuilder()

        for (i in split.indices) {
            name.append(split[i].lowercase(Locale.ROOT))
            if (i != split.size - 1)
                name.append("_")
        }

        try {
            return R.layout::class.java.getField(name.toString()).getInt(R.layout::class.java)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        return 0
    }
}