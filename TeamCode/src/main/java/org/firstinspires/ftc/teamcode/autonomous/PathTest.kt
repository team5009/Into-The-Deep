package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.hyperion.core.AutonPaths
import ca.helios5009.hyperion.core.CommandExecute
import ca.helios5009.hyperion.core.Movement
import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.instances.autonomous.MainAutonomous

@Autonomous(name = "PathTest", group = "Testing")
class PathTest: LinearOpMode() {
	private val listener = EventListener()
	override fun runOpMode() {
		val autonPaths = AutonPaths()
		val instance = MainAutonomous(this, listener)
		val pathExecutor = CommandExecute(this, listener, true)
		pathExecutor.motors = instance.motors
		pathExecutor.odometry = instance.odometry
		pathExecutor.movement = Movement(listener, pathExecutor.motors!!, pathExecutor.odometry!!)
		var seletectedPath = 0
		var gamepadDownPressed = false
		var gamepadUpPressed = false
		while (opModeInInit()) {
			val storedPaths = autonPaths.getAllPaths()
			if (gamepad1.dpad_up && !gamepadUpPressed) {
				if (seletectedPath == storedPaths.size - 1) {
					seletectedPath = 0
				} else {
					seletectedPath++
				}
				gamepadUpPressed = true
			} else if (!gamepad1.dpad_up && gamepadUpPressed) {
				gamepadUpPressed = false
			} else if (gamepad1.dpad_down && !gamepadDownPressed) {
				if (seletectedPath == 0) {
					seletectedPath = storedPaths.size - 1
				} else {
					seletectedPath--
				}
				gamepadDownPressed = true
			} else if (!gamepad1.dpad_down && gamepadDownPressed) {
				gamepadDownPressed = false
			}

			telemetry.addData("Selected Path", storedPaths[seletectedPath])
			telemetry.update()
		}

		waitForStart()

		if (opModeIsActive()) {
			pathExecutor.readPath(autonPaths.getAllPaths()[seletectedPath])
			pathExecutor.execute()
		}
	}
}
