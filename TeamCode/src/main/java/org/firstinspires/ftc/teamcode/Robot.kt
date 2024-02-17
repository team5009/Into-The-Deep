package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.abs

class Robot(private val instance : LinearOpMode) {
	val fl: DcMotorEx = instance.hardwareMap.get(DcMotorEx::class.java, "FL")
	val fr: DcMotorEx = instance.hardwareMap.get(DcMotorEx::class.java, "FR")
	val br: DcMotorEx = instance.hardwareMap.get(DcMotorEx::class.java, "BR")
	val bl: DcMotorEx = instance.hardwareMap.get(DcMotorEx::class.java, "BL")

	val leftEncoder: DcMotorEx = fl
	val rightEncoder: DcMotorEx = fr
	val backEncoder: DcMotorEx = br

	init {
		fl.direction = DcMotorSimple.Direction.REVERSE
		fr.direction = DcMotorSimple.Direction.FORWARD
		bl.direction = DcMotorSimple.Direction.REVERSE
		br.direction = DcMotorSimple.Direction.FORWARD

		fl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		fr.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		bl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		br.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

		fl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
		fr.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
		bl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
		br.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE


	}

}