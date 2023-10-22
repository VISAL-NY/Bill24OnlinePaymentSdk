plugins {
    id("com.android.application")
}

android {
    namespace = "com.bill24.bill24onlinepaymentsdk"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bill24.bill24onlinepaymentsdk"
        minSdk = 22
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Gson Convert
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Spinkit Loading
    implementation("com.wang.avi:library:2.1.3")
    //Picasso Loading image
    implementation("com.squareup.picasso:picasso:2.8")
//    implementation("com.github.bumptech.glide:glide:4.16.0")
//    annotationProcessor("com.github.bumptech.glide:glide:4.16.0")
    //Convert Data Object to Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //http log
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}