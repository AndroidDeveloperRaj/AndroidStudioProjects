/*
 * Copyright 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.calculator.trigonometry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.duy.calculator.R;
import com.duy.calculator.activities.base.AbstractEvaluatorActivity;
import com.duy.calculator.data.SampleData;
import com.duy.calculator.evaluator.EvaluateConfig;
import com.duy.calculator.evaluator.MathEvaluator;
import com.duy.calculator.evaluator.thread.Command;
import com.duy.calculator.model.TrigItem;
import com.duy.calculator.utils.ConfigApp;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import static com.duy.calculator.model.TrigItem.TRIG_TYPE.EXPAND;
import static com.duy.calculator.model.TrigItem.TRIG_TYPE.EXPONENT;
import static com.duy.calculator.model.TrigItem.TRIG_TYPE.REDUCE;

/**
 * Trigonometric Activity
 * Created by Duy on 31-Jan-17.
 */

public class TrigActivity extends AbstractEvaluatorActivity {
    public static final String TYPE = "TrigActivity";
    public static final String TAG = "TrigActivity";
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mType = intent.getIntExtra(TYPE, -1);
        if (mType == -1) {
            Toast.makeText(this, "Bundle nullable!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        init();
    }

    /**
     * - set title for activity
     * - Set text for button solve
     * - if this is first start activity or debug enable, give example data
     */
    private void init() {
        btnSolve.setText(R.string.eval);
        boolean started;
        switch (mType) {
            case EXPAND:
                setTitle(R.string.tit_trig_expand);
                started = mPreferences.getBoolean(TAG + "expand", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_EXPAND_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "expand", true).apply();
                }
                break;
            case REDUCE:
                setTitle(R.string.tit_trig_reduce);
                started = mPreferences.getBoolean(TAG + "reduce", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_REDUCE_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "reduce", true).apply();
                }
                break;
            case EXPONENT:
                setTitle(R.string.tit_trig_to_exp);
                started = mPreferences.getBoolean(TAG + "exponent", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_EXPONENT_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "exponent", true).apply();
                }
                break;

        }
    }

    @Override
    protected String getExpression() {
        TrigItem item = new TrigItem(mInputFormula.getCleanText());
        switch (mType) {
            case EXPAND:
                item.setType(EXPAND);
                break;
            case REDUCE:
                item.setType(REDUCE);
                break;
            case EXPONENT:
                item.setType(EXPONENT);
                break;
        }
        return item.getInput();
    }

    @Override
    public void clickHelp() {
    }


    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {

                String fraction = MathEvaluator.newInstance().evaluateWithResultAsTex(input,
                        EvaluateConfig.loadFromSetting(getApplicationContext())
                                .setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }
}
