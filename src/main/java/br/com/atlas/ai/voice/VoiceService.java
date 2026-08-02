package br.com.atlas.ai.voice;

public class VoiceService extends Thread {

    private final VoiceController voiceController;
    private final VoiceManager voiceManager;

    private volatile boolean running = true;

    public VoiceService(
            VoiceController voiceController,
            VoiceManager voiceManager
    ) {

        this.voiceController = voiceController;
        this.voiceManager = voiceManager;

        setName("ATLAS-VOICE-SERVICE");
        setDaemon(true);

    }

    @Override
    public void run() {

        while (running) {

            try {

                VoiceResult result =
                        voiceController.listen();

                if (result != null) {

                    voiceManager.speak(
                            result.getResponse().getMessage()
                    );

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }

    public void shutdown() {

        running = false;

    }

}