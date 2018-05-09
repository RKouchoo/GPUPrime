package primeMaker;

import java.math.BigInteger;

import com.aparapi.Kernel;
import com.aparapi.device.Device;

public class Main {

	static Kernel GPUKernel;
	static BigInteger ix;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		GPUKernel = new Kernel() {
			@Override
			public void run() {
				int max = getGlobalId();

				for (int i = 1; i < max; i++) {
					boolean isPrimeNumber = true;

					// check to see if the number is prime
					for (int j = 2; j < i; j++) {
						if (i % j == 0) {
							isPrimeNumber = false;
							break; // exit the inner for loop
						}
					}

					// print the number if prime
					if (isPrimeNumber) {
						// check to see that the prime has as many digits as max.
						if (Integer.toString(i).length() >= Integer.toString(max).length()) {
							System.out.println(i);
						}
					}
				}
				
				
			}
		};

		GPUKernel.addExecutionModes(Kernel.EXECUTION_MODE.GPU); // why is this deprecitated?
	
		try {
			double x = GPUKernel.execute(10000).getAccumulatedExecutionTimeAllThreads(Device.best());
			System.out.println("Elapsed time (Seconds): " + x / 1000);
		} catch (IllegalStateException t) {
			System.out.println("Switching to CPU Mode!"); // unfortunately this try catch does not catch that error.
		}
		
		GPUKernel.dispose();

	}

}
