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

package com.duy.calculator.model;

/**
 * LimitItem
 * Created by Duy on 29-Dec-16.
 */

public class LimitItem extends IntegrateItem {
    /**
     * Limit item
     *
     * @param input - function
     * @param to    - upper limit, x -> inf
     */
    public LimitItem(String input, String to) {
        super(input, "", to);
    }

    @Override
    public String getInput() {
        return "Limit(" + input + "," + var + " -> " + to + ")";
    }
}
