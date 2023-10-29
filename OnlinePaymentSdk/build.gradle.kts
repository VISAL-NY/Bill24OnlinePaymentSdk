import com.android.build.gradle.internal.scope.publishBuildArtifacts
import com.android.build.gradle.internal.utils.createPublishingInfoForLibrary

plugins {
    id("com.android.library")
    id("maven-publish")
}


android {
    namespace = "com.bill24.onlinepaymentsdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // << --- ADD This
        targetCompatibility = JavaVersion.VERSION_17
    }

}



dependencies {

    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.10.0")
    api("androidx.constraintlayout:constraintlayout:2.1.4")


    //Retrofit2
    api("com.squareup.retrofit2:retrofit:2.9.0")
    //Gson Convert
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    //Picasso Loading image
    api("com.squareup.picasso:picasso:2.8")
    //Convert Data Object to Gson
    api("com.google.code.gson:gson:2.10.1")
    //http log
    api("com.squareup.okhttp3:logging-interceptor:4.11.0")
    //
    api("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    //socket io
    api ("io.socket:socket.io-client:2.1.0") {
        // excluding org.json which is provided by Android
        exclude(group="org.json", module="json")
    }
}
