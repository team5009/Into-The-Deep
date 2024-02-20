package org.firstinspires.ftc.teamcode.misc

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class MenuPathSelector {
	private var currentPage = MENU_PAGE.ALLIANCE_SELECT
	private var gamepadDownPressed = false
	private var gamepadUpPressed = false
	private var gamepadAPressed = false
	private var gamepadBPressed = false

	private var selectedAlliance = 0
	private var selectedSide = 0

	private val allianceOptions = arrayOf(ALLIANCE.RED, ALLIANCE.BLUE)
	private val sideOptions = arrayOf(SIDE.LEFT, SIDE.RIGHT)

	var allianceOption = ALLIANCE.RED
	var sideOption = SIDE.LEFT
	var ready = false
	fun run(instance: LinearOpMode) {
		val t = instance.telemetry
		val g1 = instance.gamepad1
		when (currentPage) {
			MENU_PAGE.ALLIANCE_SELECT -> {
				t.addLine("Alliance Select")
				t.addData(">", allianceOptions[selectedAlliance])
				t.update()

				if (g1.dpad_up && !gamepadUpPressed) {
					if (selectedAlliance == allianceOptions.size - 1) {
						selectedAlliance = 0
					} else {
						selectedAlliance++
					}
					gamepadUpPressed = true
				} else if (!g1.dpad_up && gamepadUpPressed) {
					gamepadUpPressed = false
				} else if (g1.dpad_down && !gamepadDownPressed) {
					if (selectedAlliance == 0) {
						selectedAlliance = allianceOptions.size - 1
					} else {
						selectedAlliance--
					}
					gamepadDownPressed = true
				} else if (!g1.dpad_down && gamepadDownPressed) {
					gamepadDownPressed = false
				}

				if (g1.a && !gamepadAPressed) {
					currentPage = MENU_PAGE.SIDE_SELECT
					allianceOption = allianceOptions[selectedAlliance]
					gamepadAPressed = true
				} else if (!g1.a && gamepadAPressed) {
					gamepadAPressed = false
				}
			}
			MENU_PAGE.SIDE_SELECT -> {
				t.addLine("Side Select")
				t.addData(">", sideOptions[selectedSide])
				t.update()

				if (g1.dpad_up && !gamepadUpPressed) {
					if (selectedSide == sideOptions.size - 1) {
						selectedSide = 0
					} else {
						selectedSide++
					}
					gamepadUpPressed = true
				} else if (!g1.dpad_up && gamepadUpPressed) {
					gamepadUpPressed = false
				} else if (g1.dpad_down && !gamepadDownPressed) {
					if (selectedSide == 0) {
						selectedSide = sideOptions.size - 1
					} else {
						selectedSide--
					}
					gamepadDownPressed = true
				} else if (!g1.dpad_down && gamepadDownPressed) {
					gamepadDownPressed = false
				}

				if (g1.a && !gamepadAPressed) {
					currentPage = MENU_PAGE.CONFIRM
					sideOption = sideOptions[selectedSide]
					gamepadAPressed = true

				} else if (!g1.a && gamepadAPressed) {
					gamepadAPressed = false
				}

				if (g1.b && !gamepadBPressed) {
					currentPage = MENU_PAGE.ALLIANCE_SELECT
					gamepadBPressed = true
				} else if (!g1.b && gamepadBPressed) {
					gamepadBPressed = false
				}
			}
			MENU_PAGE.CONFIRM -> {
				t.addData("Alliance", allianceOption)
				t.addData("Side", sideOption)

				if (g1.b && !gamepadBPressed) {
					currentPage = MENU_PAGE.SIDE_SELECT
					ready = false
					gamepadBPressed = true
				} else if (!g1.b && gamepadBPressed) {
					gamepadBPressed = false
				}
			}
		}
	}
}

enum class MENU_PAGE {
	ALLIANCE_SELECT,
	SIDE_SELECT,
	CONFIRM
}

enum class ALLIANCE {
	RED, BLUE
}

enum class SIDE {
	LEFT, RIGHT
}