plugins {
    id("com.android.application")
}

android {
    namespace = "com.bill24.bill24onlinepaymentsdk"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bill24.bill24onlinepaymentsdk"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
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
    implementation(files("libs/OnlinePaymentSdk-release.aar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Gson Convert
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Picasso Loading image
    implementation("com.squareup.picasso:picasso:2.8")
    //Convert Data Object to Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //http log
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    //
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    //socket io
    implementation ("io.socket:socket.io-client:2.1.0") {
        // excluding org.json which is provided by Android
        exclude(group="org.json", module="json")
    }
}