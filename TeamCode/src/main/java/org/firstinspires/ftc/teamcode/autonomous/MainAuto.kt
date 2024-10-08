package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.hyperion.misc.Camera
import ca.helios5009.hyperion.misc.Position
import ca.helios5009.hyperion.core.AutonPaths
import ca.helios5009.hyperion.core.CommandExecute
import ca.helios5009.hyperion.core.Movement
import ca.helios5009.hyperion.misc.Alliance
import ca.helios5009.hyperion.misc.events.EventListener
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.instances.autonomous.MainAutonomous
import org.firstinspires.ftc.teamcode.misc.MenuPathSelector
import org.firstinspires.ftc.teamcode.processors.ColorProcessor

@Autonomous(name = "Main", group = "_Production")
class MainAuto: LinearOpMode() {
	private val listener = EventListener()
	override fun runOpMode() {
		val autonPaths = AutonPaths()
//		val instance = MainAutonomous(this, listener)
		val dash = FtcDashboard.getInstance()
		telemetry = MultipleTelemetry(telemetry, dash.telemetry)
		val menu = MenuPathSelector()
		val optimized = false
		val camProcessor = ColorProcessor(Alliance.BLUE, optimized)
		val cam = Camera(hardwareMap, optimized).addProcessor(camProcessor).build()
		val positionChangeTimer = ElapsedTime()

//		var position = Position.NONE
//		telemetry = MultipleTelemetry(telemetry, dash.telemetry)
//		val pathExecutor = CommandExecute(this, listener)
//		pathExecutor.motors = instance.motors
//		pathExecutor.odometry = instance.odometry
//		val movementClass = Movement(listener, pathExecutor.motors!!, pathExecutor.odometry!!, this)
//		movementClass.setControllerConstants(
//			doubleArrayOf(DriveConstants.GainSpeed, DriveConstants.AccelerationLimit, DriveConstants.DefaultOutputLimit, DriveConstants.Tolerance, DriveConstants.Deadband),
//			doubleArrayOf(StrafeConstants.GainSpeed, StrafeConstants.AccelerationLimit, StrafeConstants.DefaultOutputLimit, StrafeConstants.Tolerance, StrafeConstants.Deadband),
//			doubleArrayOf(RotateConstants.GainSpeed, RotateConstants.AccelerationLimit, RotateConstants.DefaultOutputLimit, RotateConstants.Tolerance, RotateConstants.Deadband)
//		)
//		pathExecutor.movement = movementClass
		var cameraSetup = false

		while (opModeInInit()) {
			menu.run(this)
			if (menu.ready && !cameraSetup) {
				camProcessor.alliance = menu.allianceOption
				cameraSetup = true
			} else if (!menu.ready && cameraSetup) {
				cameraSetup = false
			}

			if (cameraSetup) {
//				if (camProcessor.position != position) {
//
//				}
//				position = camProcessor.position
				telemetry.addData("Alliance", menu.allianceOption)
				telemetry.addData("Side", menu.sideOption)
				telemetry.addData("Position", camProcessor.position)
			}
			telemetry.update()
			val storedPaths = autonPaths.getAllPaths()
		}

		waitForStart()

		if (opModeIsActive()) {
//			pathExecutor.readPath(autonPaths.getAllPaths()[seletectedPath])
//			pathExecutor.execute()
		}
	}
}

