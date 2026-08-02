package br.com.atlas.ai.memory.longterm.rules;

import br.com.atlas.ai.memory.longterm.LongTermMemory;
import br.com.atlas.ai.memory.longterm.MemoryEntry;
import br.com.atlas.ai.memory.longterm.MemoryManager;

public interface MemoryRule {

    boolean matchesSave(String message);

    void save(
            String message,
            MemoryManager memoryManager
    );

    boolean matchesRecall(String message);

    MemoryEntry recall(
            LongTermMemory memory
    );

}