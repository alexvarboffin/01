#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Other - D:\android\GitHub\facebook\
#D:\\source\\CallRecorder\\app\\

#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Allow methods with the same signature, except for the return type,
# to get the same obfuscation name.

#-overloadaggressively



#-optimizationpasses 30
#-mergeinterfacesaggressively
#-dontpreverify
#-optimizations !code/simplification/arithmetic
## Put all obfuscated classes into the nameless root package.




-repackageclasses 'com.dropbox.core'#fake location



#-allowaccessmodification
#-useuniqueclassmembernames
#-keeppackagenames doNotKeepAThing
#
## Allow classes and class members to be made public.
#
#-allowaccessmodification
-obfuscationdictionary D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt

#D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt
-classobfuscationdictionary class.txt
-packageobfuscationdictionary D:\android\GitHub\facebook\proguard\examples\dictionaries\windows.txt


#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-flattenpackagehierarchy 'xxx'#xxxxx


#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class com.android.vending.licensing.ILicensingService


#-dontnote
# OkHttp and Servlet optional dependencies
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.**


-dontwarn com.google.appengine.**
-dontwarn javax.servlet.**

# Support classes for compatibility with older API versions

-dontwarn android.support.**
-dontnote android.support.**
