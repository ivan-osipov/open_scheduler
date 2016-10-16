import org.gradle.jvm.tasks.Jar

val buildNumber = project.properties["buildNumber"]

val targetMinorVersion = "1"
val currentMinorVersion = "0"

group = "org.pyjjs"
version = "0.${if(buildNumber != null) "$currentMinorVersion.$buildNumber" else "$targetMinorVersion-SNAPSHOT"}"

val akkaVersion = "2.4.2"
val groovyVersion = "2.4.7"
val findbugsVersion = "3.0.1"
val guavaVersion = "19.0"
val slf4jVersion = "1.7.21"
val logbackVersion = "1.1.7"
val springVersion = "4.3.0.RELEASE"
val junitVersion = "4.12"
val hamcrestVersion = "1.3"
val kotlin_version = "1.0.4"

buildscript {
    val kotlin_version = "1.0.4"
    repositories {
        mavenCentral()
//        maven {
//            setUrl("http://dl.bintray.com/kotlin/kotlin-dev")
//        }
    }

    dependencies {
//        classpath(kotlinModule("gradle-plugin"))
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

apply {
    plugin("kotlin")
    plugin("java")
    plugin<ApplicationPlugin>()
}

configure<ApplicationPluginConvention> {
    applicationName = "Open Scheduler"
}

configure<JavaPluginConvention> {
    setSourceCompatibility(1.8)
    setTargetCompatibility(1.8)
}

repositories {
    gradleScriptKotlin()
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib"))
    compile("com.typesafe.akka:akka-actor_2.11:$akkaVersion")
    compile("org.codehaus.groovy:groovy-all:$groovyVersion")
    compile("com.google.code.findbugs:jsr305:$findbugsVersion")
    compile("com.google.guava:guava:$guavaVersion")
    compile("org.slf4j:slf4j-api:$slf4jVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")
    compile("ch.qos.logback:logback-core:$logbackVersion")
    compile("org.springframework:spring-beans:$springVersion")
    compile("org.springframework:spring-context:$springVersion")

    testCompile("junit:junit:$junitVersion")
    testCompile("org.hamcrest:hamcrest-library:$hamcrestVersion")
}

//task<Jar>("fatJar") {
//    baseName = "${project.name}-all"
//    from(configurations.compile.map {
//        if (it.isDirectory) return@map it
//        else {
//            zipTree(it)
//        }
//    })
//    with(tasks.getByName("jar") as Jar)
//}

