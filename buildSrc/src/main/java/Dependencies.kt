object Dependencies {
    // Dagger Hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroid}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}" }

    // Room
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomRuntime}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomCompiler}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.roomKtx}" }

    // Navigation for Compose
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }

    // Hilt Navigation Compose integration
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }

    // Icons
    val materialIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.materialIconsExtended}" }

    // Retrofit

}

object Modules {
    const val utilities = ":utilities"
}