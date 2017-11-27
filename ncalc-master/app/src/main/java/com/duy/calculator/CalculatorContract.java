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

package com.duy.calculator;

import android.support.annotation.StringRes;

import java.util.List;

/**
 * Created by Duy on 24-Jun-17.
 */

public class CalculatorContract {

    public interface Display {
        void showToast(String msg);

        void showToast(@StringRes int id);

        void setPresenter(Presenter presenter);

        void displayOutput(List<String> out);

        void clear();

        void showProcessBar();

        void hideProcessBar();

        void updateFromSettings();
    }

    public interface ResourceAccess {
        String getString(@StringRes int id);
    }

    public interface Presenter {
        void updateSettings();

        Display getDisplay();

        void openSetting();

        void handleExceptions(Exception e);

        void openHistory();
    }
}
