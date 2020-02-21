package theredspy15.ltecleanerfoss.scan.strings;

import java.util.HashMap;

public class MaliciousStrings {
    public static HashMap<String, String> maliciousStrings;

    static
    {
        // all strings are reversed to avoid false positive
        maliciousStrings = new HashMap<>();
        maliciousStrings.put("tiolpsatem.moc", "Metasploit Payload");
        maliciousStrings.put("raj.daolyap", "Metasploit Payload");
        maliciousStrings.put("xed.daolyap", "Metasploit Payload");
    }
}
