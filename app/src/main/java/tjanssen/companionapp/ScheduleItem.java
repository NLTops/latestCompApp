package tjanssen.companionapp;

/**
 * Created by Tops on 13-10-2016.
 */
public class ScheduleItem {
    String room;
    String subject;
    String teacher;
    String time;

    public ScheduleItem(String room, String subject, String teacher, String time){
        this.room = room;
        this.subject = subject;
        this.teacher = teacher;
        this.time = time;
    }
}
