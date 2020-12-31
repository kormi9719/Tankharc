public class VerificationEndpoint {

    public static String getVerificationEndpoint() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));

        }

        return sb.toString();

    }
}
