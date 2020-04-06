package com.example.notalone_covid19;

public class VolunteerUser extends User {
    private String PhotoPath;
    private String description;
    private String phoneNumber;
    private String location;//check
    private boolean permissionAccess=false;

    public VolunteerUser() {
      super();
    }

    public VolunteerUser(String fullName, String email, String id, String photoPath,String description, String phoneNumber, String location) {
        super(fullName, email, id);
        PhotoPath = photoPath;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.permissionAccess=false;
    }

    public boolean isPermissionAccess() {
        return permissionAccess;
    }

    public void setPermissionAccess(boolean permissionAccess) {
        this.permissionAccess = permissionAccess;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
