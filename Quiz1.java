import java.lang.String; 
import java.lang.Math; 

public class Quiz1 {

	/** Determines the number of letters in common between Strings
	 * a and b
	 * 
	 * @param a - a String of characters 'a'-'z'
	 * @param b - a String of characters 'a'-'z'
	 * @return the number of letters in common
	 */
	public static int lettersInCommon(String a, String b){
		//place holder so that the code compiles
		int inCommon = 0; 
		int stringLength = a.length(); 


		for (int i = 0; i < stringLength; i++) {
			char evalChar = b.charAt(i);
			String evalStr = String.valueOf(evalChar);

			if (a.contains(evalStr)) {
				inCommon ++; 
			}

		}
		return inCommon;
	}

	/** Determines the minimum number of perfect marks needed 
	 * to earn a 10
	 * 
	 * @param marks - an integer array of the current marks
	 * earned in the course
	 * @return the number of perfect scores needed to 
	 * earn a 10
	 * 
	 * I know the below code doesn't run. I ran out of time and could 
	 * not figure out how to fix my types, which I believe was the problem.
	 * I had significant difficulty debugging it.  
	 */
	public static int aimToTen(int[] marks){

		final int MAXLENGTH = 50; 

		int scoresNeeded = 0; 
		int[] myArray = marks; 
		int sumArray = 0;
		int lengthArray = myArray.length;
		for (int j=0; j < lengthArray; j++) {
			sumArray += myArray[j]; }

		for (int i = 0; i < MAXLENGTH; i++) {

			double average = (((sumArray)/(lengthArray))); 
			if ((average) >= 9.5) { 
				return scoresNeeded; } 
			else {
				sumArray += 10; 
				lengthArray ++;
				scoresNeeded ++; 
			}
		}
	}


	public static void main(String[] args){

		/*This is how to run and test your code. Feel free to
		 * add as many additional tests as you want.
		 * 
		 * You do NOT need to comment this out of your final submission.
		 *
		 */

		int test1 = lettersInCommon("horse", "mouse");
		int test3 = lettersInCommon("horse", "mirth");
		int test4 = lettersInCommon("horse", "short");


		System.out.println(test1); // should print 3

		System.out.println(test3);
		System.out.println(test4);

		int test2 = aimToTen(new int[]{9, 10, 10, 10});

		System.out.println(test2); // should print 0

	}
}
