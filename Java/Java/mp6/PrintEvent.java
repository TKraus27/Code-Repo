	/** Output print event */
public final class PrintEvent extends Simulation.Event {
		private final float printInterval;

		public PrintEvent( float time, float i ) {
			super( time );
			printInterval = i;
		}
		/** Every event must provide a trigger method */
		public void trigger() {
			for( Gate g: TernaryLogic.gates ) {
				System.out.print( " " + g.printValue() );
			}
			System.out.println();

			Simulation.schedule( new PrintEvent(
				time + printInterval,
				printInterval
			) );
		}

		public static void initPrint( float i ) {
			Simulation.schedule( new PrintEvent( 0.0f, i ) );
			for( Gate g: TernaryLogic.gates ) {
				System.out.print( " " + g.name );
			}
			System.out.println();
		}
	}
