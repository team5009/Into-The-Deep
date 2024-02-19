package ca.helios5009.hyperion.core

import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class Motors(val fl: DcMotorEx, val fr: DcMotorEx, val br: DcMotorEx, val bl: DcMotorEx) {
	fun moveOld(drive: Double, strafe: Double, rotate: Double) {
		val maxPower = abs(drive) + abs(strafe) + abs(rotate)
		val max = if (maxPower < 0.15) maxPower / 0.15 else maxOf(1.0, maxPower/0.8) // 0.8 is the max power of the motors, if the number is greater than 0.8, it will be divided by 0.8

		fl.power = (drive - strafe + rotate) / max
		fr.power = (drive + strafe - rotate) / max
		bl.power = (drive - strafe - rotate) / max
		br.power = (drive + strafe + rotate) / max
	}

	fun move(theta: Double, drive: Double, strafe: Double, rotate: Double) {
		val sinValue = sin(theta - PI/4)
		val cosValue = cos(theta - PI/4)
		val power = 0.2
		val maxPower = abs(drive) + abs(strafe)
		val max = maxOf(abs(sinValue), abs(cosValue))

		var flPower = cosValue/max * power + rotate
		var frPower = sinValue/max * power + rotate
		var blPower = sinValue/max * power + rotate
		var brPower = cosValue/max * power + rotate

		if (power + abs(theta) > 1) {
			flPower /= power + rotate
			frPower /= power + rotate
			blPower /= power + rotate
			brPower /= power + rotate
		}

		fl.power = flPower
		fr.power = frPower
		bl.power = blPower
		br.power = brPower
	}
}