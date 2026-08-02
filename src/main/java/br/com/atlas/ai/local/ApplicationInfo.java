package br.com.atlas.ai.local;

public class ApplicationInfo {

    private final String id;
    private final String displayName;

    public ApplicationInfo(
            String id,
            String displayName
    ) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

}