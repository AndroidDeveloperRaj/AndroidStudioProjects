package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://bitbucket.org/axelclk/symja_android_library">bitbucket.org/axelclk/symja_android_library under the tools directory</a>.</p>
 */
public interface LegendrePRules {
  /**
   * <ul>
   * <li>index 0 - number of equal rules in <code>RULES</code></li>
	 * </ul>
	 */
  final public static int[] SIZES = { 1, 0 };

  final public static IAST RULES = List(
    IInit(LegendreP, SIZES),
    // LegendreP(x_,1)=1
    ISet(LegendreP(x_,C1),
      C1),
    // LegendreP(x_NumericQ,0):=Sqrt(Pi)/(Gamma(1/2*(1-x))*Gamma(1+x/2))
    ISetDelayed(LegendreP($p(x,NumericQ),C0),
      Times(Sqrt(Pi),Power(Times(Gamma(Times(C1D2,Plus(C1,Negate(x)))),Gamma(Plus(C1,Times(C1D2,x)))),-1))),
    // LegendreP(x_IntegerQ,-1):=(-1)^x
    ISetDelayed(LegendreP($p(x,IntegerQ),CN1),
      Power(CN1,x))
  );
}
