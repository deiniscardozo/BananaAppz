package com.overcom.bananaapp.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.ActivityMainBinding
import com.overcom.bananaapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.workspace.setText(preferences.getWorkspace())
        binding.email.setText(preferences.getEmail())

        val view = layoutInflater.inflate(R.layout.toast_error, null)
        val text: TextView = view.findViewById(R.id.toastMessage_error)
        val viewOk = layoutInflater.inflate(R.layout.toast_ok, null)
        val textOk: TextView = viewOk.findViewById(R.id.toastMessage)
        val dialog = layoutInflater.inflate(R.layout.recover_password, null)

        viewModel =
            this.let {
                ViewModelProvider(it)[MainViewModel::class.java]
            }

        viewModel.textChangedListener(binding.workspace)

        binding.login.setOnClickListener {
            viewModel.accesToDetail(
                binding.email.text.toString().trim(),
                binding.workspace.text.toString().trim()
            )

            viewModel.callServiceGetUsers(
                this, text, view,
                binding.password.text.toString(),
                binding.email.text.toString()
            )
        }

        binding.forgetpass.setOnClickListener {
            viewModel.recoverPassword(
                this, dialog, binding.email.text.toString(),
                text, view, textOk, viewOk
            )
        }
    }
}