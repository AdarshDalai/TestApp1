package com.ingenioustech.testapp1.di

import com.google.firebase.firestore.FirebaseFirestore
import com.ingenioustech.testapp1.task.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(firestore: FirebaseFirestore): TaskRepository {
        return TaskRepository(firestore)
    }

    @Provides
    @Singleton
    fun providesSupabase(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://vxyvfxlxtestbtltevts.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ4eXZmeGx4dGVzdGJ0bHRldnRzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA4NDY0NDIsImV4cCI6MjA2NjQyMjQ0Mn0.pv3_uWRyRPLn-Z7TOdm0YmmJroQ1hJBxwk-J2a_pi20"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }


}