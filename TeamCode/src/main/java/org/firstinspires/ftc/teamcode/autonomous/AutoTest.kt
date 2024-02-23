package org.firstinspires.ftc.teamcode.autonomous

import ca.helios5009.hyperion.core.CommandsParse
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous(name = "AutoTest", group = "Autonomous")
class AutoTest: LinearOpMode(){
	override fun runOpMode() {
		val parser = CommandsParse()
		if (opModeInInit()) {
			telemetry.addData("Status", "Initialized")
			telemetry.addLine("Loaded Hyperion Library")
			telemetry.update()
		}
		waitForStart()
	}

}