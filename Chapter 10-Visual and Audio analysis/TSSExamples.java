package packt;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import static java.lang.System.out;

public class TSSExamples {

    public TSSExamples() {
        System.setProperty("mbrola.base", "C:\\Books in Progress\\Java for Data Science\\Chapter 10\\Downloads\\MBROLA");
        demonstrateFreeTTS();
        demonstrateVoice();
    }

    public static void main(String[] args) {
        new TSSExamples();
    }

    public void demonstrateFreeTTS() {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");
        voice.allocate();
        voice.speak("Hello World");

        // Voices
        out.println("------Voices-------");
        Voice[] voices = vm.getVoices();
        for (Voice v : voices) {
            out.println(v);
        }

        // Voice information
        out.println();
        out.println("------Voice Information-------");
        out.println(vm.getVoiceInfo());

        out.println();
        out.println("------Alan Voice Information-------");
        Voice v = vm.getVoice("alan");
        out.println(v);
    }

    public void demonstrateVoice() {
        out.println();
        out.println("------Voice Demonstration-------");

        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");
        voice.allocate();

        out.println("Name: " + voice.getName());
        out.println("Description: " + voice.getDescription());
        out.println("Organization: " + voice.getOrganization());
        out.println("Age: " + voice.getAge());
        out.println("Gender: " + voice.getGender());
        out.println("Rate: " + voice.getRate());
        out.println("Pitch: " + voice.getPitch());
        out.println("Style: " + voice.getStyle());
        out.println();
    }
}
