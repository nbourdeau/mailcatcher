package com.dumbster.smtp;

import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;

public class MimeMessageTest {

    @Test
    public void test() throws MessagingException {
        String mime = "From: Nicolas Bourdeau <nbourdeau@diffusion.cc>\n" +
                "Subject: test\n" +
                "To: test@test.com\n" +
                "Cc: bob@bob.com, bob2@bob.com\n" +
                "Message-ID: <a47514eb-a0e0-bf9a-92a1-6a4fe1744ebd@diffusion.cc>\n" +
                "Date: Wed, 26 Sep 2018 17:07:13 -0400\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:52.0) Gecko/20100101\n" +
                " Thunderbird/52.9.1\n" +
                "MIME-Version: 1.0\n" +
                "Content-Type: multipart/mixed;\n" +
                " boundary=\"------------4E30804D75C49AE48CF07962\"\n" +
                "Content-Language: en-US\n" +
                "\n" +
                "This is a multi-part message in MIME format.\n" +
                "--------------4E30804D75C49AE48CF07962\n" +
                "Content-Type: multipart/alternative;\n" +
                " boundary=\"------------E9907A3EC7722AACD8957BAE\"\n" +
                "\n" +
                "\n" +
                "--------------E9907A3EC7722AACD8957BAE\n" +
                "Content-Type: text/plain; charset=utf-8; format=flowed\n" +
                "Content-Transfer-Encoding: 7bit\n" +
                "\n" +
                "*Hello*\n" +
                "\n" +
                "\n" +
                "--------------E9907A3EC7722AACD8957BAE\n" +
                "Content-Type: text/html; charset=utf-8\n" +
                "Content-Transfer-Encoding: 7bit\n" +
                "\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
                "  </head>\n" +
                "  <body text=\"#000000\" bgcolor=\"#FFFFFF\">\n" +
                "    <p><b>Hello</b></p>\n" +
                "  </body>\n" +
                "</html>\n" +
                "\n" +
                "--------------E9907A3EC7722AACD8957BAE--\n" +
                "\n" +
                "--------------4E30804D75C49AE48CF07962\n" +
                "Content-Type: application/json;\n" +
                " name=\"repo.json\"\n" +
                "Content-Transfer-Encoding: base64\n" +
                "Content-Disposition: attachment;\n" +
                " filename=\"repo.json\"\n" +
                "\n" +
                "eyJzY20iOiAiZ2l0IiwgIndlYnNpdGUiOiAiIiwgImhhc193aWtpIjogZmFsc2UsICJuYW1l\n" +
                "IjogIiRSRVBPIiwgImxpbmtzIjogeyJ3YXRjaGVycyI6IHsiaHJlZiI6ICJodHRwczovL2Fw\n" +
                "aS5iaXRidWNrZXQub3JnLzIuMC9yZXBvc2l0b3JpZXMvcHJvZ3Jlc3Npb25saXZlL3Rlc3Qv\n" +
                "d2F0Y2hlcnMifSwgImJyYW5jaGVzIjogeyJocmVmIjogImh0dHBzOi8vYXBpLmJpdGJ1Y2tl\n" +
                "dC5vcmcvMi4wL3JlcG9zaXRvcmllcy9wcm9ncmVzc2lvbmxpdmUvdGVzdC9yZWZzL2JyYW5j\n" +
                "aGVzIn0sICJ0YWdzIjogeyJocmVmIjogImh0dHBzOi8vYXBpLmJpdGJ1Y2tldC5vcmcvMi4w\n" +
                "L3JlcG9zaXRvcmllcy9wcm9ncmVzc2lvbmxpdmUvdGVzdC9yZWZzL3RhZ3MifSwgImNvbW1p\n" +
                "dHMiOiB7ImhyZWYiOiAiaHR0cHM6Ly9hcGkuYml0YnVja2V0Lm9yZy8yLjAvcmVwb3NpdG9y\n" +
                "aWVzL3Byb2dyZXNzaW9ubGl2ZS90ZXN0L2NvbW1pdHMifSwgImNsb25lIjogW3siaHJlZiI6\n" +
                "ICJodHRwczovL25ib3VyZGVhdUBiaXRidWNrZXQub3JnL3Byb2dyZXNzaW9ubGl2ZS90ZXN0\n" +
                "LmdpdCIsICJuYW1lIjogImh0dHBzIn0sIHsiaHJlZiI6ICJnaXRAYml0YnVja2V0Lm9yZzpw\n" +
                "cm9ncmVzc2lvbmxpdmUvdGVzdC5naXQiLCAibmFtZSI6ICJzc2gifV0sICJzZWxmIjogeyJo\n" +
                "cmVmIjogImh0dHBzOi8vYXBpLmJpdGJ1Y2tldC5vcmcvMi4wL3JlcG9zaXRvcmllcy9wcm9n\n" +
                "cmVzc2lvbmxpdmUvdGVzdCJ9LCAic291cmNlIjogeyJocmVmIjogImh0dHBzOi8vYXBpLmJp\n" +
                "dGJ1Y2tldC5vcmcvMi4wL3JlcG9zaXRvcmllcy9wcm9ncmVzc2lvbmxpdmUvdGVzdC9zcmMi\n" +
                "fSwgImh0bWwiOiB7ImhyZWYiOiAiaHR0cHM6Ly9iaXRidWNrZXQub3JnL3Byb2dyZXNzaW9u\n" +
                "bGl2ZS90ZXN0In0sICJhdmF0YXIiOiB7ImhyZWYiOiAiaHR0cHM6Ly9iaXRidWNrZXQub3Jn\n" +
                "L3Byb2dyZXNzaW9ubGl2ZS90ZXN0L2F2YXRhci8zMi8ifSwgImhvb2tzIjogeyJocmVmIjog\n" +
                "Imh0dHBzOi8vYXBpLmJpdGJ1Y2tldC5vcmcvMi4wL3JlcG9zaXRvcmllcy9wcm9ncmVzc2lv\n" +
                "bmxpdmUvdGVzdC9ob29rcyJ9LCAiZm9ya3MiOiB7ImhyZWYiOiAiaHR0cHM6Ly9hcGkuYml0\n" +
                "YnVja2V0Lm9yZy8yLjAvcmVwb3NpdG9yaWVzL3Byb2dyZXNzaW9ubGl2ZS90ZXN0L2Zvcmtz\n" +
                "In0sICJkb3dubG9hZHMiOiB7ImhyZWYiOiAiaHR0cHM6Ly9hcGkuYml0YnVja2V0Lm9yZy8y\n" +
                "LjAvcmVwb3NpdG9yaWVzL3Byb2dyZXNzaW9ubGl2ZS90ZXN0L2Rvd25sb2FkcyJ9LCAicHVs\n" +
                "bHJlcXVlc3RzIjogeyJocmVmIjogImh0dHBzOi8vYXBpLmJpdGJ1Y2tldC5vcmcvMi4wL3Jl\n" +
                "cG9zaXRvcmllcy9wcm9ncmVzc2lvbmxpdmUvdGVzdC9wdWxscmVxdWVzdHMifX0sICJmb3Jr\n" +
                "X3BvbGljeSI6ICJhbGxvd19mb3JrcyIsICJ1dWlkIjogIntmZmM1MGUwYS05NTU4LTQ4NGMt\n" +
                "YmEyNS1iOWMwZjk5YjYxOGZ9IiwgInByb2plY3QiOiB7ImtleSI6ICJESUYiLCAidHlwZSI6\n" +
                "ICJwcm9qZWN0IiwgInV1aWQiOiAiezdhYTQ2MGFkLWM2NDctNGJiYy05MGM5LWU5ZmYyZDVi\n" +
                "Yzk1MX0iLCAibGlua3MiOiB7InNlbGYiOiB7ImhyZWYiOiAiaHR0cHM6Ly9hcGkuYml0YnVj\n" +
                "a2V0Lm9yZy8yLjAvdGVhbXMvcHJvZ3Jlc3Npb25saXZlL3Byb2plY3RzL0RJRiJ9LCAiaHRt\n" +
                "bCI6IHsiaHJlZiI6ICJodHRwczovL2JpdGJ1Y2tldC5vcmcvYWNjb3VudC91c2VyL3Byb2dy\n" +
                "ZXNzaW9ubGl2ZS9wcm9qZWN0cy9ESUYifSwgImF2YXRhciI6IHsiaHJlZiI6ICJodHRwczov\n" +
                "L2JpdGJ1Y2tldC5vcmcvYWNjb3VudC91c2VyL3Byb2dyZXNzaW9ubGl2ZS9wcm9qZWN0cy9E\n" +
                "SUYvYXZhdGFyLzMyIn19LCAibmFtZSI6ICJEaWZmdXNpb24ifSwgImxhbmd1YWdlIjogIiIs\n" +
                "ICJjcmVhdGVkX29uIjogIjIwMTgtMDQtMTdUMTU6Mjg6MTcuNDM3MjI2KzAwOjAwIiwgIm1h\n" +
                "aW5icmFuY2giOiBudWxsLCAiZnVsbF9uYW1lIjogInByb2dyZXNzaW9ubGl2ZS90ZXN0Iiwg\n" +
                "Imhhc19pc3N1ZXMiOiBmYWxzZSwgIm93bmVyIjogeyJ1c2VybmFtZSI6ICJwcm9ncmVzc2lv\n" +
                "bmxpdmUiLCAiZGlzcGxheV9uYW1lIjogIlByb2dyZXNzaW9uTGl2ZSIsICJ0eXBlIjogInRl\n" +
                "YW0iLCAidXVpZCI6ICJ7YWIzYjM0MDgtZDMxZS00OTEzLWI0ZDAtYTEwMjUwYjhlMzVifSIs\n" +
                "ICJsaW5rcyI6IHsic2VsZiI6IHsiaHJlZiI6ICJodHRwczovL2FwaS5iaXRidWNrZXQub3Jn\n" +
                "LzIuMC90ZWFtcy9wcm9ncmVzc2lvbmxpdmUifSwgImh0bWwiOiB7ImhyZWYiOiAiaHR0cHM6\n" +
                "Ly9iaXRidWNrZXQub3JnL3Byb2dyZXNzaW9ubGl2ZS8ifSwgImF2YXRhciI6IHsiaHJlZiI6\n" +
                "ICJodHRwczovL2JpdGJ1Y2tldC5vcmcvYWNjb3VudC9wcm9ncmVzc2lvbmxpdmUvYXZhdGFy\n" +
                "LzMyLyJ9fX0sICJ1cGRhdGVkX29uIjogIjIwMTgtMDQtMTdUMTU6Mjg6MTcuNTY5MzM4KzAw\n" +
                "OjAwIiwgInNpemUiOiAwLCAidHlwZSI6ICJyZXBvc2l0b3J5IiwgInNsdWciOiAidGVzdCIs\n" +
                "ICJpc19wcml2YXRlIjogdHJ1ZSwgImRlc2NyaXB0aW9uIjogIiJ9\n" +
                "--------------4E30804D75C49AE48CF07962--";
        MimeMessage mimeMessage = new MimeMessage(null, new ByteArrayInputStream(mime.getBytes()));
        assertNotNull(mimeMessage);
    }

}
