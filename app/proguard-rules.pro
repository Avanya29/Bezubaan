# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.annotations.**
-keep,allowoptimization class kotlinx.serialization.** { *; }

# Room
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Hilt
-keep class dagger.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-dontwarn dagger.**
