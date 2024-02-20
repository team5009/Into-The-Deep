package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.Hyperion.misc.Camera
import ca.helios5009.Hyperion.misc.Position
import ca.helios5009.hyperion.core.AutonPaths
import ca.helios5009.hyperion.core.CommandExecute
import ca.helios5009.hyperion.core.Movement
import ca.helios5009.hyperion.misc.events.EventListener
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.instances.autonomous.MainAutonomous
import org.firstinspires.ftc.teamcode.misc.MenuPathSelector
import org.firstinspires.ftc.teamcode.processors.BlueColorProcessor
import org.firstinspires.ftc.teamcode.processors.ColorProcessor
import org.firstinspires.ftc.teamcode.processors.RedColorProcessor

@Autonomous(name = "Main", group = "_Production")
class MainAuto: LinearOpMode() {
	private val listener = EventListener()
	override fun runOpMode() {
		val autonPaths = AutonPaths()
		val instance = MainAutonomous(this, listener)
		val dash = FtcDashboard.getInstance()
		val Menu = MenuPathSelector()
		var cam: Camera? = null
		var camProcess: ColorProcessor? = null
		var position = Position.NONE
		telemetry = MultipleTelemetry(telemetry, dash.telemetry)
		val pathExecutor = CommandExecute(this, listener)
		pathExecutor.motors = instance.motors
		pathExecutor.odometry = instance.odometry
		val movementClass = Movement(listener, pathExecutor.motors!!, pathExecutor.odometry!!, this)
		movementClass.setControllerConstants(
			doubleArrayOf(DriveConstants.GainSpeed, DriveConstants.AccelerationLimit, DriveConstants.DefaultOutputLimit, DriveConstants.Tolerance, DriveConstants.Deadband),
			doubleArrayOf(StrafeConstants.GainSpeed, StrafeConstants.AccelerationLimit, StrafeConstants.DefaultOutputLimit, StrafeConstants.Tolerance, StrafeConstants.Deadband),
			doubleArrayOf(RotateConstants.GainSpeed, RotateConstants.AccelerationLimit, RotateConstants.DefaultOutputLimit, RotateConstants.Tolerance, RotateConstants.Deadband)
		)
		pathExecutor.movement = movementClass
		var cameraSetup = false

		while (opModeInInit()) {
			Menu.run(this)
			if (Menu.ready && !cameraSetup) {
				cam = Camera(hardwareMap)
				camProcess = ColorProcessor(Menu.allianceOption)
				cam.addProcessor(camProcess)
				cam.build()
				cameraSetup = true
			} else if (!Menu.ready && cameraSetup) {
				cameraSetup = false
				cam = null
				camProcess = null
			}

			if (cameraSetup) {
				position = camProcess!!.position
			}
			val storedPaths = autonPaths.getAllPaths()
		}

		waitForStart()

		if (opModeIsActive()) {
//			pathExecutor.readPath(autonPaths.getAllPaths()[seletectedPath])
			pathExecutor.execute()
		}
	}
}

enum class Alliance {
	RED, BLUE
}