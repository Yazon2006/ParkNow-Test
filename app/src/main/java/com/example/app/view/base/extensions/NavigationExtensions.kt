package com.example.app.view.base.extensions

import android.app.Activity
import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.BundleCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.app.myapplication.R
import com.example.app.view.base.BaseFragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * This class closely related to switchChildFragments method and generally it is FragmentTransaction
 * wrapper. You can configure on or more of parameters that you need.
 *
 * @param containerResId Identifier of the container whose fragment(s) are
 * to be operated.
 * @param fragment The new fragment to place in the container.
 * @param animation Use [FragmentAnimation] to create custom animation or any of existing e.g.
 * [NoFragmentAnimation], [RootFragmentSlideAnimation], [MiddleFragmentSlideAnimation]
 * @param addToBackStack FragmentTransaction will be added in back stack if it's true
 * @param clearBackStack Set true if you want totally clean back stack
 */
class FragmentAction(val containerResId: Int, val fragment: BaseFragment,
							val animation: FragmentAnimation = NoFragmentAnimation, val addToBackStack: Boolean = false,
							val clearBackStack: Boolean = false, val avoidDuplication: Boolean = false)

/**
 * This class is used for convenient animation configuration for [FragmentAction]
 * It actually uses [android.support.v4.app.FragmentTransaction.setCustomAnimations] method
 * see https://developer.android.com/reference/android/app/FragmentTransaction.html#setCustomAnimations(int,%20int,%20int,%20int)
 */
sealed class FragmentAnimation(
		val enter: Int = 0,
		val exit: Int = 0,
		val popEnter: Int = 0,
		val popExit: Int = 0
)

object NoFragmentAnimation : FragmentAnimation()
object RootFragmentSlideAnimation : FragmentAnimation(exit = R.anim.slide_to_left, popExit = R.anim.slide_to_right)
object MiddleFragmentSlideAnimation : FragmentAnimation(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right)

/**
 * This method is simplifying work with FragmentTransactions by using FragmentAction as a description
 * of desirable transaction.
 * IMPORTANT! In case if there are mixed actions with and without animations you should know that
 * order of committing transactions is very important. Actions with animation should be the first one
 */
fun Activity.switchFragments(vararg actions: FragmentAction) {
	val fragmentManager: FragmentManager = if (this is AppCompatActivity) supportFragmentManager else error("Only AppCompatActivity instance allowed here")
	val fragmentTransaction = fragmentManager.beginTransaction()
	actions
			.sortedBy { it.animation != NoFragmentAnimation }
			.forEach { fragmentAction ->
				if (fragmentAction.clearBackStack) {
					fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
				}

				val animation = fragmentAction.animation
				fragmentTransaction.setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)

				val fragmentTag = fragmentAction.fragment.navigationTag

				if (fragmentAction.avoidDuplication && fragmentManager.findFragmentByTag(fragmentTag) != null) {
					fragmentManager.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
				}

				fragmentTransaction.replace(fragmentAction.containerResId, fragmentAction.fragment, fragmentTag)

				if (fragmentAction.addToBackStack) {
					fragmentTransaction.addToBackStack(fragmentTag)
				}
			}
	fragmentTransaction.commit()
}

class FragmentArg<T : Any> : ReadWriteProperty<Fragment, T> {

	var value: T? = null

	override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
		if (value == null) {
			val args = thisRef.arguments
					?: throw IllegalStateException("Cannot read property ${property.name} if no arguments have been set")
			@Suppress("UNCHECKED_CAST")
			value = args.get(property.name) as T
		}
		return value ?: throw IllegalStateException("Property ${property.name} could not be read")
	}

	override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
		if (thisRef.arguments == null) thisRef.arguments = Bundle()

		val args = thisRef.arguments
		val key = property.name
		if (args?.containsKey(key) == true) {
			error("Impossible to change fragment arg if it was set before")
		}

		when (value) {
			is String -> args?.putString(key, value)
			is Int -> args?.putInt(key, value)
			is Short -> args?.putShort(key, value)
			is Long -> args?.putLong(key, value)
			is Byte -> args?.putByte(key, value)
			is ByteArray -> args?.putByteArray(key, value)
			is Char -> args?.putChar(key, value)
			is CharArray -> args?.putCharArray(key, value)
			is CharSequence -> args?.putCharSequence(key, value)
			is Float -> args?.putFloat(key, value)
			is Bundle -> args?.putBundle(key, value)
			is IntArray -> args?.putIntArray(key, value)
			is Binder -> args?.let { BundleCompat.putBinder(it, key, value) }
			is Parcelable -> args?.putParcelable(key, value)
			is java.io.Serializable -> args?.putSerializable(key, value)
			else -> error("Type of property ${property.name} is not supported")
		}
	}
}