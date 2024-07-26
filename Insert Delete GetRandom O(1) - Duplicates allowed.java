/*381. Insert Delete GetRandom O(1) - Duplicates allowed
RandomizedCollection is a data structure that contains a collection of numbers, possibly duplicates (i.e., a multiset). It should support inserting and removing specific elements and also reporting a random element.

Implement the RandomizedCollection class:

RandomizedCollection() Initializes the empty RandomizedCollection object.
bool insert(int val) Inserts an item val into the multiset, even if the item is already present. Returns true if the item is not present, false otherwise.
bool remove(int val) Removes an item val from the multiset if present. Returns true if the item is present, false otherwise. Note that if val has multiple occurrences in the multiset, we only remove one of them.
int getRandom() Returns a random element from the current multiset of elements. The probability of each element being returned is linearly related to the number of the same values the multiset contains.
You must implement the functions of the class such that each function works on average O(1) time complexity.

Note: The test cases are generated such that getRandom will only be called if there is at least one item in the RandomizedCollection.

 

Example 1:

Input
["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
[[], [1], [1], [2], [], [1], []]
Output
[null, true, false, true, 2, true, 1]

Explanation
RandomizedCollection randomizedCollection = new RandomizedCollection();
randomizedCollection.insert(1);   // return true since the collection does not contain 1.
                                  // Inserts 1 into the collection.
randomizedCollection.insert(1);   // return false since the collection contains 1.
                                  // Inserts another 1 into the collection. Collection now contains [1,1].
randomizedCollection.insert(2);   // return true since the collection does not contain 2.
                                  // Inserts 2 into the collection. Collection now contains [1,1,2].
randomizedCollection.getRandom(); // getRandom should:
                                  // - return 1 with probability 2/3, or
                                  // - return 2 with probability 1/3.
randomizedCollection.remove(1);   // return true since the collection contains 1.
                                  // Removes 1 from the collection. Collection now contains [1,2].
randomizedCollection.getRandom(); // getRandom should return 1 or 2, both equally likely.
 

Constraints:

-231 <= val <= 231 - 1
At most 2 * 105 calls in total will be made to insert, remove, and getRandom.
There will be at least one element in the data structure when getRandom is called. */
import java.util.*;

class RandomizedCollection {
    private Map<Integer, Set<Integer>> valueToIndices; // Maps value to the set of indices
    private List<Integer> values; // List to store elements

    public RandomizedCollection() {
        valueToIndices = new HashMap<>();
        values = new ArrayList<>();
    }
    
    public boolean insert(int val) {
        boolean notPresent = !valueToIndices.containsKey(val);
        
        if (notPresent) {
            valueToIndices.put(val, new HashSet<>());
        }
        
        valueToIndices.get(val).add(values.size());
        values.add(val);
        
        return notPresent;
    }
    
    public boolean remove(int val) {
        if (!valueToIndices.containsKey(val) || valueToIndices.get(val).isEmpty()) {
            return false;
        }
        
        // Get index of the element to remove
        int indexToRemove = valueToIndices.get(val).iterator().next();
        valueToIndices.get(val).remove(indexToRemove);
        
        // If the element to remove is not the last element in the list
        if (indexToRemove < values.size() - 1) {
            // Get the last element in the list
            int lastElement = values.get(values.size() - 1);
            
            // Move the last element to the place of the removed element
            values.set(indexToRemove, lastElement);
            valueToIndices.get(lastElement).remove(values.size() - 1);
            valueToIndices.get(lastElement).add(indexToRemove);
        }
        
        // Remove the last element
        values.remove(values.size() - 1);
        
        // Clean up if the value list is empty
        if (valueToIndices.get(val).isEmpty()) {
            valueToIndices.remove(val);
        }
        
        return true;
    }
    
    public int getRandom() {
        Random random = new Random();
        return values.get(random.nextInt(values.size()));
    }
}
