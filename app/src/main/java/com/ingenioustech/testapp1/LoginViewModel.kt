package com.ingenioustech.testapp1

import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


class LoginViewModel: ViewModel() {


        val supabase = createSupabaseClient(
            supabaseUrl = "https://vxyvfxlxtestbtltevts.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ4eXZmeGx4dGVzdGJ0bHRldnRzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA4NDY0NDIsImV4cCI6MjA2NjQyMjQ0Mn0.pv3_uWRyRPLn-Z7TOdm0YmmJroQ1hJBxwk-J2a_pi20"
        ) {
            install(Postgrest)
            install(Auth) {
                // Configure auth settings if needed

            }
        }

        val auth = supabase.auth


    suspend fun signup(email: String, password: String) {
        val user = supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        if (user != null) {
            // Handle successful signup
            Log.d("Supabase sign-up", "User signed up successfully: ${user?.email}")
        } else {
            // Handle signup failure
            Log.d("Supabase sign-up","Signup failed")
        }
    }

    suspend fun login(email: String, password: String) {
        val user = supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        if (user != null) {
            // Handle successful login
            Log.d("Supabase sign-in", "User logged in successfully: ${user}")
        } else {
            // Handle login failure
            Log.d("Supabase sign-in","Login failed")
        }
    }

}