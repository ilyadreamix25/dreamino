-printseeds obfuscation/seeds.txt
-printmapping obfuscation/mapping.txt

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations, AnnotationDefault

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class com.google.gson.examples.android.model.** { <fields>; }

-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn org.slf4j.**
-dontwarn sun.misc.**

-dontnote org.apache.commons.**

-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
   static <1>$Companion Companion;
}

-if @kotlinx.serialization.Serializable class ** {
   static **$* *;
}
-keepclassmembers class <2>$<3> {
   kotlinx.serialization.KSerializer serializer(...);
}

-if @kotlinx.serialization.Serializable class ** {
   public static ** INSTANCE;
}
-keepclassmembers class <1> {
   public static <1> INSTANCE;
   kotlinx.serialization.KSerializer serializer(...);
}