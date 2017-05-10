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

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.uiespresso.util.BaseActivityInstrumentationRule;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.catrobat.catroid.uiespresso.util.matchers.BrickCategoryListMatchers;
import org.catrobat.catroid.uiespresso.util.matchers.BrickPrototypeListMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.checkIfBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.onScriptList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class IfBrickTest {

	@Rule
	public BaseActivityInstrumentationRule<ScriptActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ScriptActivity.class, true, false);

	@Before
	public void setUp() throws Exception {

		IfLogicBeginBrick ifBeginnBrick = new IfLogicBeginBrick(0);
		IfLogicElseBrick ifElseBrick = new IfLogicElseBrick(ifBeginnBrick);
		IfLogicEndBrick ifEndBrick = new IfLogicEndBrick(ifElseBrick, ifBeginnBrick);

		ifBeginnBrick.setIfElseBrick(ifElseBrick);
		ifBeginnBrick.setIfEndBrick(ifEndBrick);

		Script script = BrickTestUtils.createProjectAndGetStartScript("IfBrickTest1");

		script.addBrick(ifBeginnBrick);
		script.addBrick(new ShowBrick());
		script.addBrick(ifElseBrick);
		script.addBrick(ifEndBrick);

		baseActivityTestRule.launchActivity(null);
	}

	@Test
	public void ifBrickTest() {
		checkSetUpBrickArrangement();
	}

	@Test
	public void ifBrickPartsTest() {
		dragBrickAtPositionToBottom(2);

		checkIfBrickAtPositionShowsString(3, R.string.brick_if_end);
		checkIfBrickAtPositionShowsString(4, R.string.brick_show);

		dragBrickAtPositionToBottom(3);

		checkIfBrickAtPositionShowsString(2, R.string.brick_if_else);
		checkIfBrickAtPositionShowsString(3, R.string.brick_show);
		checkIfBrickAtPositionShowsString(4, R.string.brick_if_end);

		dragBrickAtPositionToTop(3);

		addBrickAtPosition(IfThenLogicBeginBrick.class, 2, R.string.category_control);
		addBrickAtPosition(ShowBrick.class, 3, R.string.category_looks);

		checkIfBrickAtPositionShowsString(1, R.string.brick_show);
		checkIfBrickAtPositionShowsString(2, R.string.brick_if_begin);
		checkIfBrickAtPositionShowsString(3, R.string.brick_show);
		checkIfBrickAtPositionShowsString(4, R.string.brick_if_end);

		deleteBrickAtPosition(1);

		checkIfBrickAtPositionShowsString(1, R.string.brick_if_begin);
		checkIfBrickAtPositionShowsString(2, R.string.brick_show);
		checkIfBrickAtPositionShowsString(3, R.string.brick_if_end);
		checkIfBrickAtPositionShowsString(4, R.string.brick_if_begin);

		deleteBrickAtPosition(1);
		deleteBrickAtPosition(1);
		addBrickAtPosition(ShowBrick.class, 2, R.string.category_looks);

		checkSetUpBrickArrangement();
	}

	@Test
	public void ifLogicBeginBrickCopyTest() {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(R.id.brick_if_begin_checkbox);
		selectMultipleBricksAndPeformOperation(idList, R.string.copy);

		checkIfBrickAtPositionShowsString(1, R.string.brick_if_begin);
		checkIfBrickAtPositionShowsString(1, R.string.brick_if_begin_second_part);
		checkIfBrickAtPositionShowsString(2, R.string.brick_show);
		checkIfBrickAtPositionShowsString(3, R.string.brick_if_else);
		checkIfBrickAtPositionShowsString(4, R.string.brick_if_end);

		checkIfBrickAtPositionShowsString(5, R.string.brick_if_begin);
		checkIfBrickAtPositionShowsString(5, R.string.brick_if_begin_second_part);
		checkIfBrickAtPositionShowsString(6, R.string.brick_if_else);
		checkIfBrickAtPositionShowsString(7, R.string.brick_if_end);
		checkIfBrickAtPositionShowsString(8, R.string.brick_show);

		deleteBrickAtPosition(5);
		deleteBrickAtPosition(5);

		checkSetUpBrickArrangement();
	}

	@Test
	public void ifLogicElseBrickCopyTest() {
		copyBrickAtPosition(3);

		onView(withId(R.id.brick_if_begin_checkbox));
	}

	public void checkSetUpBrickArrangement() {
		checkIfBrickAtPositionShowsString(1, R.string.brick_if_begin);
		checkIfBrickAtPositionShowsString(1, R.string.brick_if_begin_second_part);
		checkIfBrickAtPositionShowsString(2, R.string.brick_show);
		checkIfBrickAtPositionShowsString(3, R.string.brick_if_else);
		checkIfBrickAtPositionShowsString(4, R.string.brick_if_end);
	}

	public void dragBrickAtPositionToEdge(int position, final boolean dragToTop) {
		onScriptList().atPosition(position)
				.perform(longClick()).perform(new GeneralSwipeAction(Swipe.FAST,
				GeneralLocation.TOP_CENTER,
				new CoordinatesProvider() {
					@Override
					public float[] calculateCoordinates(View view) {
						float[] coordinates = GeneralLocation.CENTER.calculateCoordinates(view);
						if (dragToTop) {
							coordinates[1] = 0;
						} else {
							coordinates[1] = view.getContext().getResources().getDisplayMetrics().heightPixels;
						}
						return coordinates;
					}
				},
				Press.FINGER));
	}

	public void dragBrickAtPositionToTop(int position) {
		dragBrickAtPositionToEdge(position, true);
	}

	public void dragBrickAtPositionToBottom(int position) {
		dragBrickAtPositionToEdge(position, false);
	}

	public void addBrickAtPosition(Class<?> brickHeaderClass, int insertPosition, int brickCategoryId) {
		onView(withId(R.id.button_add))
				.perform(click());
		onData(allOf(is(instanceOf(String.class)), is(UiTestUtils.getResourcesString(brickCategoryId))))
				.inAdapterView(BrickCategoryListMatchers.isBrickCategoryView())
				.perform(click());
		onData(is(instanceOf(brickHeaderClass))).inAdapterView(BrickPrototypeListMatchers.isBrickPrototypeView())
				.perform(click());
		onScriptList().atPosition(insertPosition)
				.perform(click());
	}

	public void deleteBrickAtPosition(int position) {
		onScriptList().atPosition(position)
				.perform(click());
		onView(withText(R.string.brick_context_dialog_delete_brick))
				.perform(click());
		onView(withText(R.string.yes))
				.perform(click());
	}

	public void selectMultipleBricksAndPeformOperation(List<Integer> brickCheckBoxIdList, int menuOperationId) {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

		onView(withText(menuOperationId))
				.perform(click());

		for (int checkBoxId:brickCheckBoxIdList) {
			onView(withId(checkBoxId))
					.perform(click());
		}
		onView(withContentDescription("Done"))
				.perform(click());

		if (menuOperationId == R.string.delete) {
			onView(allOf(withId(android.R.id.button1), withText(R.string.yes)))
					.perform(click());
		}
	}

	public void copyBrickAtPosition(int brickPosition) {
		onScriptList().atPosition(brickPosition).perform(click());

		onView(withText(R.string.brick_context_dialog_copy_brick))
				.perform(click());

		onScriptList().atPosition(brickPosition).perform(click());
	}
}
