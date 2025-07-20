package model.notification;


// This enum is used for filtering in the service/servlet layer
// It's not part of the Report entity itself, but helps categorize targets
public enum ReportTargetType {
    COURSE,
    COMMENT,
    INSTRUCTOR, // Assuming instructor is a User
    LEARNER; 

    public static ReportTargetType fromStringIgnoreCase(String value) {
        for (ReportTargetType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ReportTargetType: " + value);
    }
}
