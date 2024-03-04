package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.hyperion.misc.events.Event
import ca.helios5009.hyperion.core.CommandExecute
import ca.helios5009.hyperion.core.HyperionPath
import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.core.Movement
import ca.helios5009.hyperion.core.Odometry
import ca.helios5009.hyperion.misc.commands.EventCall
import ca.helios5009.hyperion.misc.commands.Line
import ca.helios5009.hyperion.misc.commands.Point
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
	val forward = Point(0.0, 1.0, 0.0)
	override fun runOpMode() {
		forward.useError()
		val bot = Robot(this)
		telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
		val path = HyperionPath(this, listener)
		path.odometry = Odometry(bot.leftEncoder, bot.rightEncoder, bot.backEncoder)
		path.movement = Movement(listener, Motors( bot.fl, bot.fr, bot.bl, bot.br ), path.odometry!!, this)

		listener.Subscribe(OutTakePixel())
		listener.Subscribe(ScoreYellowPixel())
		listener.Subscribe(IntakePixels())
		listener.Subscribe(OutTakePixels())
		waitForStart()
		if (opModeIsActive()) {
			path.start(Point(59.0, -13.0, 180.0))
			path.continuousLine(
				listOf(
					Point(20.0, -13.0, -150.0).useError().setLocal(),
					Point(35.0, -35.0, 135.0),
					Point(34.0, -55.0, 80.0, EventCall("Yellow_Outtake")),
				)
			)
			path.end(EventCall("done"))
		}

		if(isStopRequested) {
			listener.clearQueue()
			listener.clearScopes()
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