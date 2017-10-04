/**SubsetSum.java
 * @author Tony Kraus
 * @version 5-3-2017
 */

import java.util.*;

public class SubsetSum {

  public static boolean Solver(Integer[] sortArr) {
    int median = 0;
    for (int x = 0; x < sortArr.length; x++) {
      if (sortArr[x] > 0) {
        median = x-1;
        break;
      } else {
        median = x;
      }
    }
    if (sortArr[median] == 0) {
      return true;
    } else if (median == sortArr.length-1) {
      return false;
    }
    Integer[] arr1 = Arrays.copyOfRange(sortArr, 0, median+1);
    Integer[] arr2 = Arrays.copyOfRange(sortArr, median+1, sortArr.length);
    Set<Integer> set1 = new HashSet<Integer>(Arrays.asList(arr1));
    Set<Integer> set2 = new HashSet<Integer>(Arrays.asList(arr2));
    return Loop(set1, set2);
  }

  public static boolean Loop(Set<Integer> set1, Set<Integer> set2) {
    Set<Set<Integer>> sets1 = powerSet(set1);
    Set<Set<Integer>> sets2 = powerSet(set2);
    for (Set<Integer> vals1 : sets1) {
      if (vals1.size() > 0) {
        for (Set<Integer> vals2 : sets2) {
          if (vals2.size() > 0) {
            Set<Integer> set = new HashSet<Integer>();
            set.addAll(vals1);
            set.addAll(vals2);
            Integer num = 0;
            for (Integer x : set) {
              num += x;
            }
            if (num == 0){
              return true;
            }
          }
        }
      }
    }
    return false;
  }


  public static Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
    Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
    if (originalSet.isEmpty()) {
      sets.add(new HashSet<Integer>());
      return sets;
    }
    List<Integer> list = new ArrayList<Integer>(originalSet);
    Integer head = list.get(0);
    Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
    for (Set<Integer> set : powerSet(rest)) {
      Set<Integer> newSet = new HashSet<Integer>();
      newSet.add(head);
      newSet.addAll(set);
      sets.add(newSet);
      sets.add(set);
    }
    return sets;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Input your array: ");
    String input = sc.nextLine();
    String str = input.replace("[","").replace("]","").replace(" ","");
    String[] arr = str.split(",");
    Integer[] intarr = new Integer[arr.length];
    for (int x = 0; x < arr.length; x++) {
      intarr[x] = Integer.parseInt(arr[x]);
    }
    System.out.println(Solver(intarr));
  }
}
