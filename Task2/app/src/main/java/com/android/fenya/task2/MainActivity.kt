package com.android.fenya.task2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    companion object {
        const val ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE: String = "buisness_card_feedback_message"
    }

    private val LOG_TAG: String = MainActivity::class::simpleName.toString()

    lateinit var messageEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(LOG_TAG, "onCreate")

        setContentView(R.layout.activity_main)

        messageEdit = findViewById(R.id.feedback_message)
        val button: Button = findViewById(R.id.feedback_message_button)
        button.setOnClickListener {
            val messageString: String = messageEdit.text.toString()
            
            openEmailApp(messageString)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.i(LOG_TAG, "onSaveInstanceState")

        outState?.run {
            putString(ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE, messageEdit.text.toString())
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.i(LOG_TAG, "onRestoreInstanceState")

        messageEdit.setText(savedInstanceState?.getString(ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE))
    }

    private fun openEmailApp(messageString: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SENDTO
            intent.data = Uri.parse(getString(R.string.email_intent_scheme))
            putExtra(Intent.EXTRA_EMAIL, getString(R.string.email_address))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            putExtra(Intent.EXTRA_TEXT, messageString)
            type = "text/plain"
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send email via..."))
        } else {
            Snackbar.make(messageEdit, R.string.error_no_email_app, Snackbar.LENGTH_LONG)
        }
    }
}