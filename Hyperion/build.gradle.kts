import com.android.build.gradle.tasks.MergeSourceSetFolders
import com.nishtahir.CargoBuildTask
import com.nishtahir.CargoExtension

val ftcVersion = "9.1.0"

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("org.mozilla.rust-android-gradle.rust-android") version "0.9.3"
}

android {
	namespace = "ca.helios5009.Hyperion"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	extensions.configure(CargoExtension::class.java) {
		module = "./src/main/rust"
		libname = "hyperion"
		targets = listOf("arm")
	}

	tasks.preBuild.configure {
		dependsOn.add(tasks.withType(CargoBuildTask::class.java))
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}

	ndkVersion = "26.2.11394342"
}

dependencies {
	compileOnly("org.firstinspires.ftc:RobotCore:$ftcVersion")
	compileOnly("org.firstinspires.ftc:Hardware:$ftcVersion")
	compileOnly("org.firstinspires.ftc:FtcCommon:$ftcVersion")
	compileOnly("org.firstinspires.ftc:Vision:$ftcVersion")

	implementation("androidx.core:core-ktx:1.5.0")
	implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.10"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}

project.afterEvaluate{
	for (buildTask in tasks.withType(CargoBuildTask::class.java)) {
		tasks.withType(MergeSourceSetFolders::class.java).configureEach {
			this.inputs.dir(layout.buildDirectory.dir("rustJniLibs" + File.separator + buildTask.toolchain!!.folder))
			this.dependsOn(buildTask)
		}
	}
}