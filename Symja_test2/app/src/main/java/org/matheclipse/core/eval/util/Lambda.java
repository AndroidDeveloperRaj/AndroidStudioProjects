package org.matheclipse.core.eval.util;

import com.duy.lambda.BiPredicate;
import com.duy.lambda.Consumer;
import com.duy.lambda.Function;
import com.duy.lambda.Predicate;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorReplaceArgs;
import org.matheclipse.core.visit.VisitorReplaceSlots;

public class Lambda {
	private Lambda() {
	}

	/**
	 * <p>
	 * Replace all occurrences of Slot[&lt;index&gt;] expressions with the expression at the appropriate
	 * <code>index</code> in the given <code>slotsList</code>.
	 * </p>
	 * <p>
	 * <b>Note:</b> If a slot value is <code>null</code> the Slot will not be substituted.
	 * </p>
	 * 
	 * @param expr
	 * @param slotsList
	 *            the values for the slots.
	 * @return <code>F.NIL</code> if no substitution occurred.
	 */
	public static IExpr replaceSlots(IExpr expr, final IAST slotsList) {
		return expr.accept(new VisitorReplaceSlots(slotsList));
	}

	public static IExpr replaceSlotsOrElse(IExpr expr, final IAST slotsList, IExpr elseExpr) {
		IExpr temp = expr.accept(new VisitorReplaceSlots(slotsList));
		return temp.isPresent() ? temp : elseExpr;
	}

	/**
	 * <p>
	 * Replace all occurrences of the expressions in the given list with the appropriate <code>Slot(index)</code>.
	 * </p>
	 * 
	 * @param expr
	 * @param exprsList
	 *            the values for the slots.
	 * @return <code>F.NIL</code> if no substitution occurred.
	 */
	public static IExpr replaceArgs(IExpr expr, final IAST exprsList) {
		return expr.accept(new VisitorReplaceArgs(exprsList));
	}

	/**
	 * Remove the arguments from <code>ast</code> which give <code>true</code> for the <code>predicate</code>
	 * 
	 * @param ast
	 * @param predicate
	 * @return the cloned ast with removed elements or <code>F.NIL</code> if the <code>predicate</code> never gives
	 *         <code>true</code>.
	 */
	public static IAST remove(IAST ast, Predicate<IExpr> predicate) {
		IAST result = F.NIL;
		int size = ast.size();
		int j = 1;
		for (int i = 1; i < size; i++) {
			IExpr temp = ast.get(i);
			if (predicate.test(temp)) {
				if (!result.isPresent()) {
					result = ast.removeAtClone(i);
					continue;
				}
				result.remove(j);
				continue;
			}

			j++;
		}

		return result;
	}

	private static IExpr removeStop(IAST ast, Predicate<IExpr> predicate, Function<IExpr, IExpr> function) {
		IAST result = F.NIL;
		int size = ast.size();
		int j = 1;
		for (int i = 1; i < size; i++) {
			IExpr temp = ast.get(i);
			IExpr t = function.apply(temp);
			if (t != null) {
				return t;
			}
			if (predicate.test(temp)) {
				if (!result.isPresent()) {
					result = ast.removeAtClone(i);
					continue;
				}
				result.remove(j);
				continue;
			}

			j++;
		}

		return result;
	}

	/**
	 * Append each argument of <code>ast</code> to <code>result</code> by applying the given <code>function</code> to
	 * each argument.
	 * 
	 * @param ast
	 * @param result
	 * @param function
	 * @return
	 */
	public static IAST forEachAppend(IAST ast, IAST result, Function<IExpr, IExpr> function) {
		int size = ast.size();
		for (int i = 1; i < size; i++) {
			result.append(function.apply(ast.get(i)));
		}
		return result;
	}

	/**
	 * Consume each argument of <code>ast</code> which fulfills the <code>predicate</code>.
	 */
	public static void forEach(IAST ast, Predicate<IExpr> predicate, Consumer<IExpr> consumer) {
		int size = ast.size();
		for (int i = 1; i < size; i++) {
			IExpr t = ast.get(i);
			if (predicate.test(t)) {
				consumer.accept(t);
			}
		}
	}

	/**
	 * Compare the arguments pairwise with the <code>stopPredicate</code>. If the predicate gives <code>true</code>
	 * return the <code>stopExpr</code>. If the <code>stopPredicate</code> gives false for each pairwise comparison
	 * return the <code>resultExpr</code>
	 * 
	 * @param ast
	 * @param stopPredicate
	 * @param stopExpr
	 * @param resultExpr
	 * @return
	 */
	public static IExpr existsLeft(IAST ast, BiPredicate<IExpr, IExpr> stopPredicate, IExpr stopExpr,
			IExpr resultExpr) {
		int size = ast.size();
		for (int i = 2; i < size; i++) {
			if (stopPredicate.test(ast.get(i - 1), ast.get(i))) {
				return stopExpr;
			}
		}
		return resultExpr;
	}

	/**
	 * Tests each argument with the <code>stopPredicate</code>. If the predicate gives <code>true</code> return
	 * <code>true</code>. If the <code>stopPredicate</code> gives false for each test return <code>false</code>
	 * 
	 * @param ast
	 * @param stopPredicate
	 * @param offset TODO
	 * @return
	 */
	public static boolean exists(IAST ast, Predicate<IExpr> stopPredicate, int offset) {
		int size = ast.size();
		for (int i = offset; i < size; i++) {
			if (stopPredicate.test(ast.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean forAll(IAST ast, Predicate<IExpr> predicate, int offset) {
		int size = ast.size();
		for (int i = offset; i < size; i++) {
			if (!predicate.test(ast.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static IExpr testMap(IAST ast, Predicate<IExpr> predicate, Function<IExpr, IExpr> function) {
		IAST result = F.NIL;
		int size = ast.size();
		for (int i = 1; i < size; i++) {
			IExpr temp = ast.get(i);
			if (predicate.test(temp)) {
				if (!result.isPresent()) {
					result = ast.clone();
				}
				temp = function.apply(temp);
				if (temp != null) {
					result.set(i, function.apply(temp));
				}
				continue;
			}
		}

		return result;
	}

	private static IExpr testMap2(IAST list, Predicate<IExpr> predicate, Function<IExpr, IExpr> function1,
			Function<IExpr, IExpr> function2) {
		IAST result = F.NIL;
		int size = list.size();
		for (int i = 1; i < size; i++) {
			IExpr temp = list.get(i);
			if (predicate.test(temp)) {
				if (!result.isPresent()) {
					result = list.clone();
					for (int j = 0; j < i; j++) {
						result.set(j, function2.apply(temp));
					}
				}
				result.set(i, function1.apply(temp));
				continue;
			}
			if (result != null) {
				result.set(i, function2.apply(temp));
			}
		}

		return result;
	}
}
