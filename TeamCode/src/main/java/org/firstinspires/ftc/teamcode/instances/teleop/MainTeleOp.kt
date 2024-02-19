package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.core.Odometry
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Robot

class MainTeleOp(private val instance : LinearOpMode) {
	val bot = Robot(instance)
	val odometry = Odometry(bot.leftEncoder, bot.rightEncoder, bot.backEncoder)
	init {
		odometry.setConstants(
			OdometryValues.distanceBack, OdometryValues.distanceLeftRight
		)
	}

	fun gamepadOne(gamepad: Gamepad) {

	}

	fun gamepadTwo(gamepad: Gamepad) {

	}

	fun driveControls(gamepad: Gamepad) {
		val forward = -gamepad.left_stick_y.toDouble()
		val turn = gamepad.right_stick_x.toDouble()
		val strafe = gamepad.left_stick_x.toDouble()
	}
}

@Config
object OdometryValues {
	@JvmField var distanceBack = 6.25;
	@JvmField var distanceLeftRight = 11.5652;
}