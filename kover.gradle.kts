plugins {
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}

koverMerged {
    enable()

    filters { // common filters for all default Kover tasks
        classes { // common class filter for all default Kover tasks
            excludes += listOf(
                "**/*BuildConfig.*",
                "**/*$*",
                "**/*Hilt_*.class",
                "hilt_**",
                "dagger/hilt/**",
                "**/*Dagger.*",
                "di/**",
                "dispatcher/**"
            )
        }
    }

    xmlReport {
        onCheck.set(true)
    }

    htmlReport {
        onCheck.set(true)
    }
}