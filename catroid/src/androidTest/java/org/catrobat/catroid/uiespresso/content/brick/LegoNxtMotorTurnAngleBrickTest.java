/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.content.brick;

import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.uiespresso.util.BaseActivityInstrumentationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.checkIfBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.enterValueInFormulaTextFieldOnBrickAtPosition;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.checkIfSpinnerOnBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.clickAndSelectFromSpinnerOnBrickAtPosition;

@RunWith(AndroidJUnit4.class)
public class LegoNxtMotorTurnAngleBrickTest {
	private int brickPosition;
	private final int initialAngle = 90;
	private final int newValue = 40;


	@Rule
	public BaseActivityInstrumentationRule<ScriptActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ScriptActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		BrickTestUtils.createProjectAndGetStartScript("legoNxtMotorTurnAngleBrickTest1").addBrick(new
				LegoNxtMotorTurnAngleBrick(LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A, initialAngle));
		brickPosition = 1;
		baseActivityTestRule.launchActivity(null);
	}

	@Test
	public void nxtMotorTurnAngleTest() {
		checkIfBrickAtPositionShowsString(0, R.string.brick_when_started);
		checkIfBrickAtPositionShowsString(brickPosition, R.string.nxt_brick_motor_turn_angle);

		enterValueInFormulaTextFieldOnBrickAtPosition(newValue, R.id.motor_turn_angle_edit_text, brickPosition);

		checkIfSpinnerOnBrickAtPositionShowsString(R.id.lego_motor_turn_angle_spinner, brickPosition, R.string.nxt_motor_a);


		clickAndSelectFromSpinnerOnBrickAtPosition(R.id.lego_motor_turn_angle_spinner, brickPosition, R.string
				.nxt_motor_b);
		checkIfSpinnerOnBrickAtPositionShowsString(R.id.lego_motor_turn_angle_spinner, brickPosition, R.string
				.nxt_motor_b);

		enterValueInFormulaTextFieldOnBrickAtPosition(newValue, R.id.motor_turn_angle_edit_text, brickPosition);


		clickAndSelectFromSpinnerOnBrickAtPosition(R.id.lego_motor_turn_angle_spinner, brickPosition, R.string
				.nxt_motor_c);
		checkIfSpinnerOnBrickAtPositionShowsString(R.id.lego_motor_turn_angle_spinner, brickPosition, R.string
				.nxt_motor_c);

		enterValueInFormulaTextFieldOnBrickAtPosition(newValue, R.id.motor_turn_angle_edit_text, brickPosition);

	}
}
