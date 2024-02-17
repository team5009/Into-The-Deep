package ca.helios5009.hyperion.core

import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
class Motors(val fl: DcMotorEx, val fr: DcMotorEx, val br: DcMotorEx, val bl: DcMotorEx) {
	fun move(drive: Double, strafe: Double, rotate: Double) {
		val maxPower = abs(drive) + abs(strafe) + abs(rotate)
		val max = if (maxPower < 0.2) maxPower / 0.2 else maxOf(1.0, maxPower/0.8) // 0.8 is the max power of the motors, if the number is greater than 0.8, it will be divided by 0.8

		fl.power = (drive + strafe + rotate) / max
		fr.power = (drive - strafe - rotate) / max
		bl.power = (drive - strafe + rotate) / max
		br.power = (drive + strafe - rotate) / max
	}
}