package com.bsuir.cryptowallet.common.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() = BindingDelegate(T::class.java, this)

class BindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding == null) binding = bindingClass.getMethod("bind", View::class.java)
            .invoke(null, thisRef.requireView()) as T
        return binding!!
    }

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { _viewLifecycleOwner ->
                    _viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }
}