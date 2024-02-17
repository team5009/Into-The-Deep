package ca.helios5009.hyperion.core

import ca.helios5009.hyperion.misc.euclideanDistance
import ca.helios5009.hyperion.misc.commands.Point
import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.cos
import kotlin.math.sin

class Movement(private val listener: EventListener, private val bot: Motors, private val odometry: Odometry) {

	private lateinit var driveController : ProportionalController
	private lateinit var strafeController : ProportionalController
	private lateinit var rotateController : ProportionalController
	var target: Point = Point(0.0, 0.0, 0.0)

	fun start(points: MutableList<Point>) {
		target = points.removeLast()
		points.forEach() {
			listener.call(it.event.message)
			resetController()
			val isBigger = euclideanDistance(odometry.location, target) < euclideanDistance(it, target)
			goToPosition(it, isBigger)
		}
		listener.call(target.event.message)
		goToEndPoint()
	}
	fun setControllerConstants(drive: List<Double>, strafe: List<Double>, rotate: List<Double>) {
		driveController = ProportionalController(drive[0], drive[1], drive[2], drive[3], drive[4])
		strafeController = ProportionalController(strafe[0], strafe[1], strafe[2], strafe[3], strafe[4])
		rotateController = ProportionalController(rotate[0], rotate[1], rotate[2], rotate[3], rotate[4], true)
	}

	private fun goToPosition(nextPoint: Point, isBigger: Boolean? = null) {


		while(odometry.calculate() && (isBigger == null || isBigger == euclideanDistance(odometry.location, target) < euclideanDistance(nextPoint, target))) {
			val magnitude = euclideanDistance(nextPoint, odometry.location)
			if (magnitude == 0.0) {
				break
			}

			val speedFactor = euclideanDistance(odometry.location, target)

			val deltaX = (nextPoint.x - odometry.location.x) / magnitude * speedFactor
			val deltaY = nextPoint.y - odometry.location.y / magnitude * speedFactor
			val deltaRot = nextPoint.rot - odometry.location.rot

			val theta = -odometry.location.rot
			val dx = deltaX * cos(theta) - deltaY * sin(theta)
			val dy = deltaX * sin(theta) + deltaY * cos(theta)

			val drive = driveController.getOutput(dx)
			val strafe = strafeController.getOutput(dy)
			val rotate = rotateController.getOutput(deltaRot)

			bot.move(drive, strafe, rotate)

			if (isBigger == null) {
				break
			}
		}
	}

	private fun goToEndPoint() {
		val timer = ElapsedTime()
		resetController()
		while (true) {
			goToPosition(target)
			if (driveController.inPosition && strafeController.inPosition && rotateController.inPosition) {
				if (timer.seconds() > 0.5) {
					break
				}
			} else {
				timer.reset()
			}
		}
	}

	private fun resetController() {
		driveController.reset()
		strafeController.reset()
		rotateController.reset()
	}
}