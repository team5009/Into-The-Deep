package ca.helios5009.hyperion.core

import ca.helios5009.hyperion.misc.commands.Point
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin



class Odometry (private val leftEncoder: DcMotorEx, private val rightEncoder: DcMotorEx, private val backEncoder: DcMotorEx) {

	var location = Point(0.0, 0.0, 0.0)

	private var distanceLeftRight = 0.0
	private var distanceBack = 0.0
	private val encoderConstant: Double = PI * 2.0 / 2000.0

	private var currentLeft = 0.0
	private var currentRight = 0.0
	private var currentBack = 0.0

	private var lastLeft = 0.0
	private var lastRight = 0.0
	private var lastBack = 0.0

	fun setConstants(distanceBack: Double, distanceLeftRight: Double) {
		this.distanceBack = distanceBack
		this.distanceLeftRight = distanceLeftRight
	}

	fun calculate(): Boolean {
		lastBack = currentBack
		lastLeft = currentLeft
		lastRight = currentRight

		currentLeft = leftEncoder.currentPosition.toDouble()
		currentRight = rightEncoder.currentPosition.toDouble()
		currentBack = backEncoder.currentPosition.toDouble()

		val deltaLeft = currentLeft - lastLeft
		val deltaRight = currentRight - lastRight
		val deltaBack = currentBack - lastBack

		val deltaTheta = ((deltaLeft - deltaRight) / distanceLeftRight) * encoderConstant
		val deltaX = ((deltaLeft + deltaRight) / 2.0) * encoderConstant
		val deltaY = deltaBack * encoderConstant - distanceBack * deltaTheta

		val theta = location.rot + deltaTheta
		location.x += deltaX * cos(theta) + deltaY * sin(theta)
		location.y += deltaX * sin(theta) - deltaY * cos(theta)
		location.rot += deltaTheta

		return true
	}

	fun getRotDegrees() : Double {
		return location.rot * 180 / (PI)
	}

	fun setOrigin (x:Double, y: Double, rot: Double) {
		location = Point(x, y, rot)
	}

}