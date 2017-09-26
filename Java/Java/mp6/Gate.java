/** Gates are linked by wires
 *  @see Wire
 *  @see MinGate
 *  @see MaxGate
 *  @see NotGate
 *  @see IsTGate
 *  @see IsFGate
 *  @see IsUGate
 *  @see TernaryLogic#findGate(String)
 *
 *  The logic used to simulate a gate is largely lifted from the
 *  posted solution to Homework 8.
 */

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.PriorityQueue;

public abstract class Gate {
	private final LinkedList <Wire> outgoing = new LinkedList <Wire> ();
	/** setter method to add outgoing wires to this gate
	 *  @param w the wire that connects from this gate
	 */
	public void addOutgoing( Wire w ) {
		outgoing.add( w );
	}

	private int incount = 0;    // how many inputs are connected?
	/** setter method to add incoming wires to this gate
	 *  @param w the wire that connects to this gate
	 */
	public void addIncoming( Wire w ) {
		// actually, we don't need w, but the public doesn't know that
		incount = incount + 1;
	}

	public final String name;   // the name of the gate

	// Gripe:  We'd like to declare the following as final, but can't
	// because they're set by the subclass constructor
	public       int    inputs; // the type of the gate
	public       float  delay;  // the type of the gate

	/** constructor needed by subclasses to set the final fields of gate
	 */
	protected Gate( String n ) {
		name = n;
	}

	/** factory method scans and processes one gate definition
	 */
	public static Gate newGate( Scanner sc ) {
		String myName = ScanSupport.nextName( sc );
		if ("".equals( myName )) {
			Errors.warn(
				"gate has no name"
			);
			sc.nextLine();
			return null;
		}

		if (TernaryLogic.findGate( myName ) != null) {
			Errors.warn(
				"Gate '" + myName +
				"' redefined."
			);
			sc.nextLine();
			return null;
		}

		String myType = ScanSupport.nextName( sc );
		if ("min".equals( myType )) {
			return new MinGate( sc, myName );

		} else if ("max".equals( myType )) {
			return new MaxGate( sc, myName );

		} else if ("neg".equals( myType )) {
			return new NegGate( sc, myName );

		} else if ("isfalse".equals( myType )) {
			return new IsFGate( sc, myName );

		} else if ("istrue".equals( myType )) {
			return new IsTGate( sc, myName );

		} else if ("isunknown".equals( myType )) {
			return new IsUGate( sc, myName );

		} else {
			Errors.warn(
				"Gate '" + myName +
				"' '" + myType +
				"' has an illegal type."
			);
			sc.nextLine();
			return null;
		}
	}

	/** Scan gate's delay and line end to finish initialization;
	 *  this is always called at the end of the subclass constructor
	 */
	protected final void finishGate( Scanner sc ) {
		delay = sc.nextFloat();
		if (delay != delay) { // really asks if delay == NaN
			Errors.warn(
				this.myString() + " -- has no delay"
			);
		} else if (delay < 0.0f) {
			Errors.warn(
				this.myString() + " -- has negative delay."
			);
		}
		ScanSupport.lineEnd( sc, () -> this.myString() );
	}

	/** output this Gate in a format like that used for input
	 *  it would have been nice to use toString here, but can't protect it
	 */
	protected String myString() {
		return(
			"gate " + name
		);
	}

	// ***** Logic Simulation *****

	// for logic values, inputCount[v] shows how many inputs have that value
	int inputCounts[] = new int[3];
	// this gate's most recently computed output value
	int output;
	int oldout;

	/** Sanity check for gates */
	public void check() {
		if (incount < inputs) {
			Errors.warn(
				this.myString() + " -- has missing inputs."
			);
		} else if (incount > inputs) {
			Errors.warn(
				this.myString() + " -- has too many inputs."
			);
		}

		// initially, all the inputs are unknown
		inputCounts[0] = 0;
		inputCounts[1] = inputs;
		inputCounts[2] = 0;

		// and initially, the output is unknown
		output = 1;
		oldout = output;
		// some subclasses will add to this behavior
	}
	
	public String printValue() {
		String[] arr = new String[9];
		arr[0] = "|    ";
		arr[1] = "|_   ";
		arr[2] = "|___ ";
		arr[3] = " _|  ";
		arr[4] = "  |  ";
		arr[5] = "  |_ ";
		arr[6] = " ___|";
		arr[7] = "   _|";
		arr[8] = "    |";
		int value =
			(oldout == 0 && output == 0) ? 0:
			(oldout == 0 && output == 1) ? 1:
			(oldout == 0 && output == 2) ? 2:
			(oldout == 1 && output == 0) ? 3:
			(oldout == 1 && output == 1) ? 4:
			(oldout == 1 && output == 2) ? 5:
			(oldout == 2 && output == 0) ? 6:
			(oldout == 2 && output == 1) ? 7 : 8;
		return arr[value];
	}

	/** Every subclass must define this function;
	 *  @return the new logic value, a function of <TT>inputCounts</TT>;
	 */
	protected abstract int logicValue();

	/** Event service routine called when the input to a gate changes
	 *  @param time the time at which the input changes
	 *  @param old the previous logic value carried over this input
	 *  @param new the new logic value carried over this input
	 */
	public static final class InputChangeEvent extends Simulation.Event {
		private final Gate g;
		private final int oldv;
		private final int newv;
		
		public InputChangeEvent( float time, Gate g, int ov, int nv ) {
			super( time );
			this.g = g;
			this.oldv = ov;
			this.newv = g.logicValue();
			g.inputCounts[ov]--;
			g.inputCounts[nv]++;
		}
		public void trigger() {
			if (g.output != newv) {
				final int old = g.output;
				Simulation.schedule(new OutputChangeEvent( time + g.delay, g, old, newv )
				);
				g.output = newv;
			}
		}
	};

	/** Event service routine called when the output of a gate changes
	 *  @param time the time at which the output changes
	 *  @param old the previous logic value of this gate's output
	 *  @param new the new logic value of this gate's output
	 */
	public static final class OutputChangeEvent extends Simulation.Event {
		private final Gate g;
		private final int oldv;
		private final int newv;

		public OutputChangeEvent( float time, Gate g, int ov, int nv) {
			super( time );
			this.g = g;
			this.oldv = ov;
			this.newv = nv;
		}

		public void trigger() {
			for ( Wire w : g.outgoing) {
				Simulation.schedule( new Wire.InputChangeEvent( time, w, oldv, newv));
			}
		}
	}
}
