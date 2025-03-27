package info.jbcs.minecraft.utilities;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LangUtils {
    private static final Set<String> longTokensToNotMutate = ImmutableSet.of(
            "with"
    );

    private static final Set<String> shortTokensToMutate = ImmutableSet.of(
            "oak",
            "ice",
            "red"
    );

    public static String applyCapitalization(final String input) {
        final StringBuilder output = new StringBuilder(input.length());
        final List<String> tokens = Arrays.asList(input.split(" "));
        final int numTokens = tokens.size();

        for (int i = 0; i < numTokens; i++) {
            final String token = tokens.get(i);
            if (i > 0) {
                output.append(' ');
            }
            final int length = token.length();

            if (i < numTokens - 1 && (longTokensToNotMutate.contains(token) || (length < 4 && !shortTokensToMutate.contains(token)))) {
                output.append(token);
            } else if (!token.isEmpty()) {
                output.append(Character.toUpperCase(token.charAt(0)));

                if (length > 1) {
                    output.append(token.substring(1));
                }
            }
        }

        return output.toString();
    }
}
