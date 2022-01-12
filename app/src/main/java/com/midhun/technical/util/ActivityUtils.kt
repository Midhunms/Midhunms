package com.midhun.technical.util

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.ArrayList


/**
 * This provides methods to help Activities load their UI.
 */
object ActivityUtils {

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.
     */
    fun addFragmentToActivity(fragmentManager: FragmentManager?, fragment: Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
        transaction.add(frameId, fragment, fragment.javaClass.name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.
     */
    fun replaceFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int
    ) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
        transaction.replace(frameId, fragment, fragment.javaClass.name)
        transaction.commit()
    }

    /**
     * @param activity
     * @param aClass
     * @param bundle
     * @param isFinish
     */
    fun startActivity(activity: Activity, aClass: Class<*>, bundle: Bundle?, isFinish: Boolean) {
        val intent = Intent(activity, aClass)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        activity.startActivity(intent)
        if (isFinish) {
            activity.finish()
        }
    }

    /**
     * @param activity
     * @param aClass
     * @param bundle
     * @param isFinish
     */
    fun startActivityForResult(
        activity: Activity,
        aClass: Class<*>,
        bundle: Bundle?,
        isFinish: Boolean,
        requestCode: Int
    ) {
        val intent = Intent(activity, aClass)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        activity.startActivityForResult(intent, requestCode)
        if (isFinish) {
            activity.finish()
        }
    }

}
