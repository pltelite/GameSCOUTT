package com.gamescout.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    // Simulated local database for testing/demo
    private val registeredUsers = mutableMapOf<String, String>()
    private val userBacklog = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnFetchGames = findViewById<Button>(R.id.btnFetchGames)
        val btnAddLibrary = findViewById<Button>(R.id.btnAddLibrary)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvDataOutput = findViewById<TextView>(R.id.tvDataOutput)

        // Password Encryption (SHA-256 Hashing)
        fun hashPassword(password: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        // Register Action with Input Validation
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || !email.contains("@")) {
                tvStatus.text = "Error: Please enter a valid email address."
                return@setOnClickListener
            }
            if (password.length < 6) {
                tvStatus.text = "Error: Password must be at least 6 characters."
                return@setOnClickListener
            }

            val encryptedPass = hashPassword(password)
            registeredUsers[email] = encryptedPass
            tvStatus.text = "Success: Account created! (Password hashed: ${encryptedPass.take(8)}...)"
        }

        // Login Action
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                tvStatus.text = "Error: Enter both email and password."
                return@setOnClickListener
            }

            val encryptedPass = hashPassword(password)
            if (registeredUsers[email] == encryptedPass) {
                tvStatus.text = "Logged in successfully as: $email"
            } else {
                tvStatus.text = "Error: Invalid credentials or account not registered."
            }
        }

        // REST API Simulation (Fetch Games)
        btnFetchGames.setOnClickListener {
            tvDataOutput.text = """
                === Available Games (REST API) ===
                1. Elden Ring [Genre: Action RPG | Rating: 4.9]
                2. The Witcher 3 [Genre: RPG | Rating: 4.8]
                3. Cyberpunk 2077 [Genre: Sci-Fi | Rating: 4.5]
            """.trimIndent()
        }

        // Database Action (Add to Backlog)
        btnAddLibrary.setOnClickListener {
            userBacklog.add("Elden Ring")
            tvDataOutput.text = """
                === My Library / Backlog ===
                - Items in Backlog: ${userBacklog.joinToString(", ")}
                - Total Saved Games: ${userBacklog.size}
            """.trimIndent()
        }
    }
}