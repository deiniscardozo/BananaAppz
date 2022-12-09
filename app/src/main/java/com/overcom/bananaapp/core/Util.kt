package com.overcom.bananaapp.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object Util : AppCompatActivity() {

    fun callNotSuccessful(context: Context, call: String?, text: TextView, view: View) {

        Toast(context).apply {
            setGravity(Gravity.CENTER, 0, 0)
            setView(view)
            text.text = call
        }.show()
    }

    fun intentFragment(fragment: Fragment, manager: FragmentManager, viewInt: Int) {
        val transaction = manager.beginTransaction()

        transaction.replace(viewInt, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun intentActivity(context: Context?, activity: Class<*>) {
        ContextCompat.startActivity(
            context!!,
            Intent(context, activity),
            Bundle()
        )

        finish()
    }

    fun intentActivityPut(
        context: Context?,
        activity: Class<*>,
        values: String?,
        values1: String?
    ) {
        ContextCompat.startActivity(
            context!!,
            Intent(context, activity)
                .putExtra("values", values)
                .putExtra("values1", values1),
            Bundle()
        )

        finish()
    }
}