package br.com.atlas.ai.memory.longterm;

import br.com.atlas.ai.memory.MemoryQueryType;

import java.util.ArrayList;
import java.util.List;


public class MemoryRetriever {


    public List<MemoryEntry> retrieve(
            MemoryQueryType type,
            LongTermMemory memory
    ){

        List<MemoryEntry> result =
                new ArrayList<>();


        switch(type){


            case NAME -> {

                addIfExists(
                        result,
                        memory,
                        "usuario.nome"
                );

            }


            case FAVORITE_COLOR -> {

                addIfExists(
                        result,
                        memory,
                        "usuario.cor_favorita"
                );

            }


            case PROJECT -> {

                addIfExists(
                        result,
                        memory,
                        "usuario.projeto"
                );

            }


            case PROGRAMMING_LANGUAGE -> {

                addIfExists(
                        result,
                        memory,
                        "usuario.linguagem_programacao"
                );

            }


            case GENERAL_MEMORY -> {

                result.addAll(
                        memory.getAll()
                                .values()
                );

            }


            case NONE -> {

            }

        }


        return result;

    }



    private void addIfExists(
            List<MemoryEntry> result,
            LongTermMemory memory,
            String key
    ){

        MemoryEntry entry =
                memory.getEntry(key);


        if(entry != null){

            result.add(entry);

        }

    }

}