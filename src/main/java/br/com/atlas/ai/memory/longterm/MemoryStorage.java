package br.com.atlas.ai.memory.longterm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.util.Map;


public class MemoryStorage {

    private final ObjectMapper mapper =
            new ObjectMapper();

    private final File file =
            new File("atlas_memory.json");


    public void save(LongTermMemory memory)
            throws Exception {


        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(
                        file,
                        memory.getAll()
                );

    }


    public void load(LongTermMemory memory)
            throws Exception {


        if (!file.exists()) {
            return;
        }


        MapType type =
                TypeFactory
                        .defaultInstance()
                        .constructMapType(
                                Map.class,
                                String.class,
                                MemoryEntry.class
                        );


        Map<String,MemoryEntry> saved =
                mapper.readValue(
                        file,
                        type
                );


        saved.forEach(
                (key, entry) -> {

                    memory.save(
                            entry.getKey(),
                            entry.getValue(),
                            entry.getCategory(),
                            entry.getImportance()
                    );

                }
        );

    }

}