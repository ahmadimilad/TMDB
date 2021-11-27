#============ Original =================================================================================================
-keep class * extends androidx.fragment.app.Fragment{}
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit

#============ Own Project =================================================================================================
-keep class com.tmdb.utils.error_handler.ErrorModel { *; }
-keep class com.tmdb.model.** { *; }
-keep class com.tmdb.domain.entities.** { *; }
-keep class com.tmdb.data.remote.model.** { *; }
-keep class com.tmdb.data.local.models.** { *; }
-keep class com.tmdb.data.models.** { *; }

#============ Glide =================================================================================================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}