package primeMaker;

import java.util.List;
import java.util.ArrayList;
import com.aparapi.Kernel;

public class Main {

	static Kernel GPUKernel;

	public static void main(String[] args) {
		List<Integer> computeList = new ArrayList<Integer>();

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
						if (Integer.toString(i).length() > Integer.toString(max).length()) {
							System.out.print(i + "\n ");
						}
					}
				}

			}
		};

		GPUKernel.execute(10000);
		GPUKernel.dispose();

	}

}
