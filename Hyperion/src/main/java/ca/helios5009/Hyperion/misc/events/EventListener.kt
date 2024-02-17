package ca.helios5009.hyperion.misc.events

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference

class EventListener {
	var value = AtomicReference("")
	private val triggerFunctions = mutableListOf<Event>()

	fun Subscribe(function: Event) {
		triggerFunctions.add(function)
	}

	fun call(newValue: String) {
		value.set(newValue.lowercase())
		triggerFunctions.forEach {
			if (it.event.lowercase() == newValue) {
				CoroutineScope(Dispatchers.Default).launch {
					it.run()
				}
			}
		}
	}

}

