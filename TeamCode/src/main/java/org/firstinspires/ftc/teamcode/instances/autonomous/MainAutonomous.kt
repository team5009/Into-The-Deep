package org.firstinspires.ftc.teamcode.instances.autonomous

import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.core.Odometry
import ca.helios5009.hyperion.misc.events.Event
import ca.helios5009.hyperion.misc.events.EventListener
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.autonomous.listener

class MainAutonomous(private val instance: LinearOpMode, listener: EventListener) {
	private val bot = Robot(instance)
	val odometry = Odometry(bot.leftEncoder, bot.rightEncoder, bot.backEncoder)

	val motors = Motors(bot.fl, bot.fr, bot.bl, bot.br)
	init {
		odometry.setConstants(OdometryValues.distanceBack, OdometryValues.distanceLeftRight)
		listener.Subscribe(OutTakePixels())

	}

	class OutTakePixels: Event("Outtake_Pixels") {
		override suspend fun run() {
			listener.call("Purple_outtake")
		}
	}
}

@Config
object OdometryValues {
	@JvmField var distanceBack = 6.25;
	@JvmField var distanceLeftRight = 11.5652;
}