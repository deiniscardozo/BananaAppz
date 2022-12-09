package com.overcom.bananaapp.ui.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overcom.bananaapp.BananaApp
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.MessageDigestUtil
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.data.repositories.MainRepository
import com.overcom.bananaapp.ui.view.activities.HomeActivity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    fun textChangedListener(workspaces: EditText){
        workspaces.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(!workspaces.text.isNullOrEmpty()){

                    Handler().postDelayed({
                        validateWorkspace(workspaces)
                    }, 3000)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun validateWorkspace(workspaces: EditText){
        viewModelScope.launch {
            val call = repository.validateWorkspace(workspaces.text.toString().trim())

            if (call.isSuccessful && call.body()?.id == null){
                workspaces.error = "Espacio de Trabajo no Existe"
            }
        }
    }

    fun callServiceGetUsers(context: Context, text: TextView, view: View,
                            password: String, email: String) {

        viewModelScope.launch {
            val email = email
            val passwordMD5 = MessageDigestUtil.md5(password)
            val call = repository.callServiceGetUsers(email, passwordMD5)
            val users = call.body()

            if (call.isSuccessful) {
                preferences.saveToken(users?.token.toString())
                preferences.saveUserID(users?.user_id.toString())
                preferences.saveUserName(users?.user?.contact?.name.toString())
                preferences.saveUserImage(users?.user?.image_name.toString())
                preferences.saveClient(users?.third_id.toString())

                Util.intentActivity(context, HomeActivity::class.java)

            }  else { Util.callNotSuccessful(context, call.errorBody()!!.string(), text, view) }
        }
    }

    private fun forgotPassword(email: String, context: Context, text: TextView, view: View,
                       textOk: TextView, viewOk: View) {

        viewModelScope.launch {
            val email = email
            val call = repository.forgetPassword(email)

            if (call.isSuccessful) {

                Util.callNotSuccessful(context, "Se le ha enviado un correo con la contraseña." +
                        "No olvide Revisar la Carpeta Spam", textOk, viewOk)

            } else { Util.callNotSuccessful(context, call.errorBody()!!.string(), text, view) }
        }
    }

    fun recoverPassword(context: Context, dialog: View, email: String,
                        text: TextView, view: View,
                        textOk: TextView, viewOk: View) {

        val builder = AlertDialog.Builder(context, R.style.CustomDialogTheme)
        val emailBt = dialog.findViewById<TextView>(R.id.emailRP)
        val workspace = dialog.findViewById<EditText>(R.id.workspaceRP)
        textChangedListener(workspace)

        builder.setView(dialog).setIcon(R.drawable.logo_banana)
            .setTitle(
                Html.fromHtml(
                "<font color='#000000'><b>Reestablecer Contraseña</b></font>"))
            .setMessage(R.string.Email_RP)
            .setPositiveButton(
                R.string.OK,
                DialogInterface.OnClickListener { dialog, id ->

                    BananaApp.preferences.saveEmail(emailBt.text.toString())

                    forgotPassword(email, context, text, view, textOk, viewOk)
                    dialog.cancel()
                })
            .setNegativeButton(
                R.string.Cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        builder.show()
        builder.create()
    }

    fun accesToDetail(email: String, workspace: String) {
        preferences.saveWorkspace(workspace.trim())
        preferences.saveEmail(email.trim())
        preferences.saveFirstRun(true)

    }
}