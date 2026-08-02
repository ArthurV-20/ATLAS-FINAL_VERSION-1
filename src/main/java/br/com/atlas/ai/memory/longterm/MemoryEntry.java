package br.com.atlas.ai.memory.longterm;

public class MemoryEntry {

    private String key;

    private String value;

    private MemoryCategory category;

    private int importance;


    public MemoryEntry(){

    }


    public MemoryEntry(
            String key,
            String value,
            MemoryCategory category,
            int importance
    ){

        this.key = key;
        this.value = value;
        this.category = category;
        this.importance = importance;

    }


    public String getKey(){
        return key;
    }


    public String getValue(){
        return value;
    }


    public void setValue(String value){
        this.value = value;
    }


    public MemoryCategory getCategory(){
        return category;
    }


    public int getImportance(){
        return importance;
    }

}