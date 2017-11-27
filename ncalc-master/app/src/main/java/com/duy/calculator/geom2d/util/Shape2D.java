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

/* File Shape2D.java
 *
 * Project : Java Geometry Library
 *
 * ===========================================
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY, without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. if not, write to :
 * The Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

// package

package com.duy.calculator.geom2d.util;

import com.duy.calculator.geom2d.GeometricObject2D;
import com.duy.calculator.geom2d.Point2D;

public interface Shape2D extends GeometricObject2D {

    /**
     * The constant used for testing results.
     */
    public final static double ACCURACY = 1e-12;

    /**
     * Checks if the shape contains the planar point defined by (x,y).
     */
    public abstract boolean contains(double x, double y);

    /**
     * Checks if the shape contains the given point.
     */
    public abstract boolean contains(Point2D p);

    /**
     * Returns the distance of the shape to the given point, or the distance of
     * point to the frontier of the shape in the case of a plain shape.
     */
    public abstract double distance(Point2D p);

    /**
     * Returns the distance of the shape to the given point, specified by x and
     * y, or the distance of point to the frontier of the shape in the case of
     * a plain (i.e. fillable) shape.
     */
    public abstract double distance(double x, double y);

    /**
     * Returns true if the shape is bounded, that is if we can draw a finite
     * rectangle enclosing the shape. For example, a straight line or a parabola
     * are not bounded.
     */
    public abstract boolean isBounded();

    /**
     * Returns true if the shape does not contain any point. This is the case
     * for example for PointSet2D without any point.
     *
     * @return true if the shape does not contain any point.
     */
    public abstract boolean isEmpty();
}



