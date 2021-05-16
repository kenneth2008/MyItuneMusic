package com.myitune.music

import android.app.PendingIntent.getActivity
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.myitune.music.view.home.HomeAct

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(HomeAct::class.java)

    @Test
    fun useAppContext() {

        activityRule.activity.runOnUiThread {



            activityRule.activity.albumDataManager.attach(object:Observer{
                override fun update(o: Observable?, arg: Any?) {
                    val size= activityRule.activity.albumDataManager.dataList.size
                    Log.i("Test", "Fire, size:$size")
                    assertNotNull(size)
                    assertTrue(size>0)
                }

            })

//            onView(withId(R.id.btnTabBookMark))
//                .perform(ViewActions.click())
        }




    }

}