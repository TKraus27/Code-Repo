import java.util.*;

public class Diagonal {
	public static List<List<Integer>> DiagonalLeft(Integer[][] arr) {
		List<List<Integer>> lists = new LinkedList<>();
		for (int a = 0; a < arr.length; a++) {
			List<Integer> diag1 = new LinkedList<>();
			for (int x = 0; x < arr.length-a; x++) {
				diag1.add(arr[arr.length-(x+a+1)][x]);
			}
			lists.add(diag1);
			if (a != 0) {
				List<Integer> diag2 = new LinkedList<>();
				for (int x = 0; x < arr.length-a; x++) {
					diag2.add(arr[arr.length-(x+1)][x+a]);
				}
				lists.add(diag2);
			}
		}
	return lists;
	}

	public static List<List<Integer>> DiagonalRight(Integer[][] arr) {
	List<List<Integer>> lists = new LinkedList<>();
	for (int a = 0; a < arr.length; a++) {
		List<Integer> diag1 = new LinkedList<>();
		for (int x = 0; x < arr.length-a; x++) {
			diag1.add(arr[x][x+a]);
		}
		lists.add(diag1);
		if (a != 0) {
			List<Integer> diag2 = new LinkedList<>();
			for (int x = 0; x < arr.length-a; x++) {
				diag2.add(arr[x+a][x]);
			}
			lists.add(diag2);
		}
	}
	return lists;
	}

	public static void main(String[] args) {
		Integer[][] arr = new Integer[7][7];
		Random rn = new Random();
		for (int x = 0; x < 7; x++) {
			for (int a = 0; a < 7; a++) {
				arr[x][a] = rn.nextInt(10) + 1;
			}
		}
		System.out.println("Input: ");
		for (int x = 0; x < arr.length; x++) {
			System.out.println(Arrays.toString(arr[x]));
		}
		System.out.println("Output Left: ");
		List<List<Integer>> lists1 = DiagonalLeft(arr);
		List<List<Integer>> lists2 = DiagonalRight(arr);
		for (List<Integer> x : lists1) {
			System.out.println(Arrays.toString(x.toArray()));
		}
		System.out.println("Output Right: ");
		for (List<Integer> x : lists2) {
			System.out.println(Arrays.toString(x.toArray()));
		}
	}
}
