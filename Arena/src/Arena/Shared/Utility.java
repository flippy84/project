package Arena.Shared;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

public class Utility {
    public static String getBase64String(InputStream in) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int inByte;

        while ((inByte = in.read()) != -1) {
            byteStream.write(inByte);
        }

        return Base64.getEncoder().encodeToString(byteStream.toByteArray());
    }
}
