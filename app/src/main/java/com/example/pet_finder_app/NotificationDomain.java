package com.example.pet_finder_app;

public class NotificationDomain {
    private int img_avatar;
    private String notif_name, notifi_descrip, notifi_time;

    public NotificationDomain() {
    }

    public NotificationDomain(int img_avatar, String notif_name, String notifi_descrip, String notifi_time) {
        this.img_avatar = img_avatar;
        this.notif_name = notif_name;
        this.notifi_descrip = notifi_descrip;
        this.notifi_time = notifi_time;
    }

    public int getImg_avatar() {
        return img_avatar;
    }

    public void setImg_avatar(int img_avatar) {
        this.img_avatar = img_avatar;
    }

    public String getNotif_name() {
        return notif_name;
    }

    public void setNotif_name(String notif_name) {
        this.notif_name = notif_name;
    }

    public String getNotifi_descrip() {
        return notifi_descrip;
    }

    public void setNotifi_descrip(String notifi_descrip) {
        this.notifi_descrip = notifi_descrip;
    }

    public String getNotifi_time() {
        return notifi_time;
    }

    public void setNotifi_time(String notifi_time) {
        this.notifi_time = notifi_time;
    }
}
