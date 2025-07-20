/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.notification;

import static model.notification.ReportType.values;

/**
 *
 * @author LEGION
 */
public enum ReportType {
    INAPPROPRIATE_HARMFUL_CONTENT,
    INAPPROPRIATE_OTHER,
    MISCONDUCT,
    POLICY_VIOLATION,
    SPAM,
    OTHER;

    public static ReportType fromStringIgnoreCase(String value) {
        for (ReportType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ReportType: " + value);
    }
}
