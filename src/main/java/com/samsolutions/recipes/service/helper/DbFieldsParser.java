package com.samsolutions.recipes.service.helper;

public class DbFieldsParser {
    public static String getDtoFieldFromDb(String dbField) {
        while (dbField.contains("_")) {
            StringBuilder stringBuilder = new StringBuilder(dbField);
            String upCase = stringBuilder.substring(stringBuilder.indexOf("_") + 1,
                    stringBuilder.indexOf("_") + 2).toUpperCase();
            stringBuilder = stringBuilder.replace(stringBuilder.indexOf("_") + 1,
                    stringBuilder.indexOf("_") + 2, upCase);
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.indexOf("_"));
            return stringBuilder.toString();
        }
        return null;
    }
}
