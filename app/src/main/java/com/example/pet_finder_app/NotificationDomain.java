package com.example.pet_finder_app;

public class NotificationDomain {
    private int img_avatar;
    private String notifi_descrip, notifi_time,notifi_type,toUserId,fromUserId;

    public NotificationDomain() {
    }

    public NotificationDomain(int img_avatar, String notifi_descrip, String notifi_time,String notifi_type,String toUserId,String fromUserId) {
        this.img_avatar = img_avatar;
        this.notifi_descrip = notifi_descrip;
        this.notifi_time = notifi_time;
        this.notifi_type = notifi_type;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }

    public int getImg_avatar() {
        return img_avatar;
    }

    public void setImg_avatar(int img_avatar) {
        this.img_avatar = img_avatar;
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

    public String getNotifi_type() {
        return notifi_type;
    }

    public void setNotifi_type(String notifi_type) {
        this.notifi_type = notifi_type;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromId) {
        this.fromUserId = fromId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toId) {
        this.toUserId = toId;
    }
}
