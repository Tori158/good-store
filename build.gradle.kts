// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0" apply false
    //프로젝트 수준의 build.gradle.kts 파일에 플러그인을 종속 항목으로 추가합니다.
    id("com.google.gms.google-services") version "4.4.1" apply false
}
