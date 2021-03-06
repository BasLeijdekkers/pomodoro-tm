/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.greeneyes.project.pomidoro.model;

/**
 * (Note that {@link com.intellij.openapi.actionSystem.ActionManager#addTimerListener(int, com.intellij.openapi.actionSystem.TimerListener)} 
 * won't work as a timer callback)
 *
 * User: dima
 * Date: May 30, 2010
 */
public class ControlThread extends Thread {
	private final PomodoroModel model;
	private volatile boolean shouldStop;

	public ControlThread(PomodoroModel model) {
		this.model = model;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!shouldStop) {
			try {
				model.updateState();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			} catch (RuntimeException e) {
				// this thread shouldn't be destroyed in case there are coding errors
				e.printStackTrace(); // TODO report exception
			}
		}
	}

	public void shouldStop() {
		shouldStop = true;
	}
}
