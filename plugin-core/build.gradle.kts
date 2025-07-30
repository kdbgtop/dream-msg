import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    // -- bukkit-versions --
    project(":plugin-core:nms").dependencyProject.subprojects.forEach {
        implementation(it)
    }

    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    // -- dream-platform --
    implementation("cc.dreamcode.platform:bukkit:1.13.4")
    implementation("cc.dreamcode.platform:bukkit-config:1.13.4")
    implementation("cc.dreamcode.platform:dream-command:1.13.4")
    implementation("cc.dreamcode.platform:persistence:1.13.4")

    // -- dream-utilties --
    implementation("cc.dreamcode:utilities-adventure:1.5.7")

    // -- dream-notice --
    implementation("cc.dreamcode.notice:bukkit:1.7.1")
    implementation("cc.dreamcode.notice:bukkit-serializer:1.7.1")

    // -- dream-command --
    implementation("cc.dreamcode.command:bukkit:2.2.2")

    // -- dream-menu --
    implementation("cc.dreamcode.menu:bukkit:1.4.3")
    implementation("cc.dreamcode.menu:bukkit-serializer:1.4.3")

    // -- tasker (easy sync/async scheduler) --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")
}

tasks.withType<ShadowJar> {

    archiveFileName.set("Dream-Template-${project.version}.jar")
    mergeServiceFiles()

    relocate("com.cryptomorin", "pl.kdbg.msg.libs.com.cryptomorin")
    relocate("eu.okaeri", "pl.kdbg.msg.libs.eu.okaeri")
    relocate("net.kyori", "pl.kdbg.msg.libs.net.kyori")

    relocate("cc.dreamcode.platform", "pl.kdbg.msg.libs.cc.dreamcode.platform")
    relocate("cc.dreamcode.utilities", "pl.kdbg.msg.libs.cc.dreamcode.utilities")
    relocate("cc.dreamcode.menu", "pl.kdbg.msg.libs.cc.dreamcode.menu")
    relocate("cc.dreamcode.command", "pl.kdbg.msg.libs.cc.dreamcode.command")
    relocate("cc.dreamcode.notice", "pl.kdbg.msg.libs.cc.dreamcode.notice")

    relocate("org.bson", "pl.kdbg.msg.libs.org.bson")
    relocate("com.mongodb", "pl.kdbg.msg.libs.com.mongodb")
    relocate("com.zaxxer", "pl.kdbg.msg.libs.com.zaxxer")
    relocate("org.slf4j", "pl.kdbg.msg.libs.org.slf4j")
    relocate("org.json", "pl.kdbg.msg.libs.org.json")
    relocate("com.google.gson", "pl.kdbg.msg.libs.com.google.gson")

    minimize {
        parent!!.project(":plugin-core:nms").subprojects.forEach {
            exclude(project(it.path))
        }
    }

    transform(PropertiesFileTransformer::class.java) {
        paths.set(listOf("META-INF/native-image/org.mongodb/bson/native-image.properties"))
        mergeStrategy.set(PropertiesFileTransformer.MergeStrategy.Append)
    }
}