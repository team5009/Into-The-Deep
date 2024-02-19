package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.Robot

@Autonomous(name = "MotorTest", group = "Test")
class MotorTest: LinearOpMode() {
	override fun runOpMode() {
		val bot = Robot(this)

		waitForStart()
		if (opModeIsActive()) {
			moveWheel(bot.fl)
			moveWheel(bot.fr)
			moveWheel(bot.bl)
			moveWheel(bot.br)
		}
	}

	fun moveWheel(x: DcMotorEx) {
		x.power = 1.0
		sleep(1000)
		x.power = 0.0
	}
}