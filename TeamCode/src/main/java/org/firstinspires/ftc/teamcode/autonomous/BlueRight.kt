package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.hyperion.misc.events.Event
import ca.helios5009.hyperion.core.CommandExecute
import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.core.Movement
import ca.helios5009.hyperion.core.Odometry
import ca.helios5009.hyperion.misc.events.EventListener
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.Robot

val listener = EventListener()
@Autonomous(name = "Blue Right", group = "Autonomous")
class BlueRight: LinearOpMode() {
	override fun runOpMode() {
		val bot = Robot(this)
		telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
		val pathExecutor = CommandExecute(this, listener)
		pathExecutor.readPath("blueLeft")
		pathExecutor.motors = Motors( bot.fl, bot.fr, bot.bl, bot.br )
		pathExecutor.odometry = Odometry(bot.leftEncoder, bot.rightEncoder, bot.backEncoder)
		pathExecutor.movement = Movement(listener, pathExecutor.motors!!, pathExecutor.odometry!!)

		listener.Subscribe(OutTakePixel())
		listener.Subscribe(ScoreYellowPixel())
		listener.Subscribe(IntakePixels())
		listener.Subscribe(OutTakePixels())
		waitForStart()
		if (opModeIsActive()) {
			pathExecutor.execute()
		}
	}

	class OutTakePixel(): Event("Purple_outtake") {
		override suspend fun run() {
			for (i in 0..2) {
				println("Purple Outtake: ${2-i}")
				delay(1000)
			}
			listener.call("Purple_outtake_finish")
		}
	}

	class ScoreYellowPixel: Event("Yellow_outtake") {
		override suspend fun run() {
			for (i in 0..1) {
				println("Yellow Outtake: ${2-i}")
				delay(2000)
			}
			listener.call("Yellow_outtake_finish")
		}
	}

	class IntakePixels: Event("Intake_Pixel") {
		override suspend fun run() {
			for (i in 0..1) {
				println("Intaking Pixel: ${i + 1}")
				delay(3000)
			}
			listener.call("Intake_Pixel_Finish")
		}
	}

	class OutTakePixels: Event("Pixel_Outtake") {
		override suspend fun run() {
			for (i in 0..1) {
				println("Outtaking Pixel: ${i + 1}")
				delay(5000)
			}
			listener.call("Pixel_Outtake_Finish")
		}
	}

}