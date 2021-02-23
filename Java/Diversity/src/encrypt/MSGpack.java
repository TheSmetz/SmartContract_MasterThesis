package encrypt;

import org.msgpack.*;
import org.msgpack.template.Templates;

import java.io.IOException;

public class MSGpack {
    private static MessagePack msgpack = new MessagePack();

    public static byte[] serialize(String src) throws IOException {
        byte[] raw = msgpack.write(src);
        return raw;
    }

    public static String deserialize(byte[] raw) throws IOException {
        String dst = msgpack.read(raw, Templates.TString);
        return dst;
    }
}
