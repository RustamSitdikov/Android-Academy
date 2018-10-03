package com.android.fenya.task2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    companion object {
        const val ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE: String = "buisness_card_feedback_message"
    }

    private val LOG_TAG: String = MainActivity::class::simpleName.toString()

    private val DATA_SCHEME: String = "mailto:"
    private val EMAIL_ADDRESS: String = "sitdikov@phystech.edu"
    private val EMAIL_SUBJECT: String = "feedback"

    lateinit var message: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(LOG_TAG, "onCreate")

        setContentView(R.layout.activity_main)

        message = findViewById(R.id.feedback_message)

        val button: Button = findViewById(R.id.feedback_message_button)
        button.setOnClickListener {
            val body: String = message.text.toString()

            val intent = Intent().apply {
                action = Intent.ACTION_SENDTO
                intent.data = Uri.parse(DATA_SCHEME)
                putExtra(Intent.EXTRA_EMAIL, EMAIL_ADDRESS)
                putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
                putExtra(Intent.EXTRA_TEXT, body)
                type = "text/plain" // message/rfc822
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Send email via..."))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.i(LOG_TAG, "onSaveInstanceState")

        outState?.run {
            putString(ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE, message.text.toString())
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.i(LOG_TAG, "onRestoreInstanceState")

        message.setText(savedInstanceState?.getString(ARGS_BUISNESS_CARD_FEEDBACK_MESSAGE))
    }
}