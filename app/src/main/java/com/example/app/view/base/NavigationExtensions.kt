package com.example.app.view.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.app.myapplication.R

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
fun Fragment.switchChildFragments(vararg actions: FragmentAction) {
	val fragmentTransaction = childFragmentManager.beginTransaction()
	actions
			.sortedBy { it.animation != NoFragmentAnimation }
			.forEach { fragmentAction ->
				if (fragmentAction.clearBackStack) {
					childFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
				}

				val animation = fragmentAction.animation
				fragmentTransaction.setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)

				val fragmentTag = fragmentAction.fragment.navigationTag

				if (fragmentAction.avoidDuplication && childFragmentManager.findFragmentByTag(fragmentTag) != null) {
					childFragmentManager.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
				}

				fragmentTransaction.replace(fragmentAction.containerResId, fragmentAction.fragment, fragmentTag)

				if (fragmentAction.addToBackStack) {
					fragmentTransaction.addToBackStack(fragmentTag)
				}
			}
	fragmentTransaction.commit()
}
