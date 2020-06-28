object Dependencies {
    // Kotlin JDK Dependency
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"

    // App Compat & Material Design Dependency
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialVersion}"
    const val swipeToRefresh =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayoutVersion}"

    // Navigation Component Dependencies
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKTXVersion}"
    const val navigationUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationFragmentKTXVersion}"

    // Lifecycle Dependency
    const val lifecycleExt =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensionsVersion}"
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycleExtensionsVersion}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKTXVersion}"
    const val lifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLiveDataKTXVersion}"
    const val lifecycleCommon =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleExtensionsVersion}"

    // Picasso Dependency
    const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"

    //KTX Dependency
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKTXVersion}"

    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"

    //Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerAndroidVersion}"
    const val daggerAndroidSupport =
        "com.google.dagger:dagger-android-support:${Versions.daggerAndroidVersion}"
    const val daggerProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.daggerAndroidVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerAndroidVersion}"
    const val daggerAnnotationProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"

    //ViewPager 2
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2Version}"

    // Custom View
    const val sweetAlert =
        "com.github.f0ris.sweetalert:library:${Versions.sweetAlertLibraryVersion}"

    //Leak Canary
    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryAndroidVersion}"

    //CameraX
    const val cameraX = "androidx.camera:camera-core:${Versions.cameraVersion}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraVersion}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraViewVersion}"
    const val cameraExtension = "androidx.camera:camera-extensions:${Versions.cameraEXTVersion}"

    //Room
    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomAnnotationProcessor = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    //Firebase
    const val googleAnalytics =
        "com.google.firebase:firebase-analytics:${Versions.analyticsVersion}"
    const val googleCrashlitycs =
        "com.google.firebase:firebase-crashlytics:${Versions.crashlyticsVersion}"
}