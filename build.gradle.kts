buildscript {
    extra.apply {
        // UI
        set("composeVersion", "1.3.0")
        set("composeBomVersion", "2022.12.00")
        set("composeActivityVersion", "1.6.1")
        set("composeConstraintLayoutVersion", "1.0.1")
        set("composeNavVersion", "2.5.3")
        set("shimmerVersion", "1.0.3")

        // Coroutines, async etc.
        set("coreKtxVersion", "1.9.0")
        set("lifecycleRuntimeKtxVersion", "2.5.1")

        // HTTP and JSON
        set("gsonVersion", "2.10")
        set("retrofitVersion", "2.9.0")
        set("coilVersion", "2.2.2")

        set("ktorVersion", "2.2.1")
        set("serializationVersion", "1.4.1")

        // DI
        set("daggerHiltVersion", "2.44.2")

        // Hash
        set("commonsCodecVersion", "1.15")
        set("commonsLangVersion", "3.12.0")
    }

    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}

plugins {
    id("com.android.application")      version "7.3.1"  apply false
    id("com.android.library")          version "7.3.1"  apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10" apply false
}