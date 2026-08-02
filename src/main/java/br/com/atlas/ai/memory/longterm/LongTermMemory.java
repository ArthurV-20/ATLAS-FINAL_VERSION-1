package br.com.atlas.ai.memory.longterm;

import java.util.HashMap;
import java.util.Map;

public class LongTermMemory {

    private final Map<String, MemoryEntry> memories =
            new HashMap<>();


    public void save(
            String key,
            String value,
            MemoryCategory category,
            int importance
    ){

        memories.put(
                key,
                new MemoryEntry(
                        key,
                        value,
                        category,
                        importance
                )
        );

    }


    public String get(String key){

        MemoryEntry entry =
                memories.get(key);

        if(entry == null){
            return null;
        }

        return entry.getValue();

    }


    public boolean contains(String key){

        return memories.containsKey(key);

    }


    public Map<String, MemoryEntry> getAll(){

        return memories;

    }


    public void remove(String key){

        memories.remove(key);

    }
    public MemoryEntry getEntry(String key){

        return memories.get(key);

    }
}