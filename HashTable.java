public class HashTable
{
    // Here table is nothing but collection of entries. contain key and value pair.
    private Entry[] table;
    
    // size of the hashtable.
    private int size;
    
    // you have default storage as a 8 then what is current storage value.
    private int numElements;
    
    // hashtable initial size.
    private static final int INITIAL_SIZE = 8;
    
    // Constructor
    public HashTable() {
        table = new Entry[INITIAL_SIZE];
        size = INITIAL_SIZE;
        numElements = 0;
    }
    
    // Act as a hashtable class.
    private static class Entry{
        String key;
        String value;
        Entry next; // To handle collisions
         
        Entry(String key, String value){
            this.key = key;
            this.value = value;
            
            this.next = null; // act as a pointer to the hashtable
           
        }
        
    }
    
    // Hash function: Simple sum of ASCII values of characters in the key ?
    private int hash(String key) {
        int hashValue = 0;
        
        int prime = 31; // Prime multiplier for better distribution
        
        for (int i = 0; i < key.length(); i++) {
            hashValue = (hashValue * prime + key.charAt(i)) % size;
        }
        return Math.abs(hashValue); // Ensure positive index
    }

    private void resize() {
    size = size * 2; // Double the size
    Entry[] newTable = new Entry[size];

    // Rehash all entries and place them in the new table
    for (Entry entry : table) {
        while (entry != null) {
            int newIndex = Math.abs(entry.key.hashCode()) % size; // Use new size for hashing
            Entry newEntry = new Entry(entry.key, entry.value);
            newEntry.next = newTable[newIndex]; // Handle collisions
            newTable[newIndex] = newEntry;

            entry = entry.next; // Move to next in linked list
        }
    }

    table = newTable; // Update table reference to the new resized table
}
    
    
    // How to check hashtable storage: Number of elements / size of table
    private double loadFactor() {
        return (double) numElements / size;
    }
    
    
    void put(String key, String value){
        
        // loadFactor means it storage fill at 70% of it then hashtable size get changed
        if (loadFactor() > 0.7) {
            resize();
        }
        
        int index = hash(key);
        
        // String default size is null means inside that hashtable we have an strorage to store key-value pair.
        if(table[index]==null){
            table[index] = new Entry(key, value);
        }
        else {
            Entry current = table[index];

            // Traverse to update existing key or add new entry
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update existing key
                    return;
                }
                if (current.next == null) break;
                current = current.next;
            }

            // Add new entry to the end of the chain
            current.next = new Entry(key, value);
        }
        numElements++;

    }
    
    // Retrieve a value by key    
    public String get(String key) {
    int index = hash(key);
    Entry current = table[index];

    while (current != null) {
        if (current.key.equals(key)) {
            return current.value;
        }
        current = current.next;
    }
    return null; // Return null if key is not found
    }
    
    // Remove a key-value pair
    public String remove(String key) {
    int index = hash(key);
    Entry current = table[index];
    Entry prev = null;

    while (current != null) {
        if (current.key.equals(key)) {
            if (prev == null) {
                table[index] = current.next; // Remove first entry
            } else {
                prev.next = current.next; // Remove middle or last entry
            }
            numElements--;
            return current.value;
        }
        prev = current;
        current = current.next;
    }
    return null;
    }
    
    
        // Print the Hash table 
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            Entry current = table[i];
            while (current != null) {
                System.out.println("Index " + i + ": Key = " + current.key + ", Value = " + current.value);
                current = current.next;
            }
        }
    }
    
	public static void main(String[] args) {
	    
	   // I am going to access non static methods
		HashTable m = new HashTable();
		
// 		Tring to insert the data into hashtable
        m.put("Shrikant", "Maharastra");
        m.put("Aditya", "Gujrat");
        m.put("Veeresh", "Karnataka");
        m.put("Rohan", "Hydrabad");
        
        //Tring to Retrieve values by key
        System.out.println(m.get("Shrikant"));  
        System.out.println(m.get("Aditya")); 
        System.out.println(m.get("Veeresh"));   
        System.out.println(m.get("Rohan")); 
        
        // Remove a key-value pair
        System.out.println(m.remove("Rohan")); 
        System.out.println(m.get("Rohan"));     
        
        
        // Resize testing: adding more elements to trigger resizing
        for (int i = 0; i < 20; i++) {
            m.put("key" + i, "value" + i+2);
        }
        
        
        // Print the current state of the hash table
        m.printTable();
        // i removed the dog thats why not showing in output
        
		
		
	}
}