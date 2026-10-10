package com.example.quickmath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MathQuestion {
    private static final Pattern PREFIX = Pattern.compile("(?i)\\bquick\\s*math\\s*:");
    private static final Pattern EXPRESSION = Pattern.compile("(?<![\\w.])(-?\\d+(?:\\.\\d+)?)\\s*([+*/×÷−-])\\s*(-?\\d+(?:\\.\\d+)?)(?:\\s*=\\s*\\?)?");

    private MathQuestion() { }

    public static String solve(String message) {
        if (message == null || !PREFIX.matcher(message).find()) return null;
        Matcher matcher = EXPRESSION.matcher(message);
        if (!matcher.find()) return null;
        try {
            BigDecimal a = new BigDecimal(matcher.group(1));
            BigDecimal b = new BigDecimal(matcher.group(3));
            BigDecimal answer = switch (matcher.group(2)) {
                case "+" -> a.add(b);
                case "-", "−" -> a.subtract(b);
                case "*", "×" -> a.multiply(b);
                case "/", "÷" -> {
                    if (b.compareTo(BigDecimal.ZERO) == 0) yield null;
                    yield a.divide(b, 8, RoundingMode.HALF_UP).stripTrailingZeros();
                }
                default -> null;
            };
            return answer == null ? null : answer.stripTrailingZeros().toPlainString();
        } catch (NumberFormatException | ArithmeticException ignored) {
            return null;
        }
    }
}
