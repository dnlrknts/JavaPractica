import java.util.Date;

public class Letter
{
    private int id;
    private int senderId;
    private int recipientId;
    private String topic;
    private String content;
    private Date departureDate;

    public Letter(int id, int senderId, int recipientId, String topic, String content, Date departureDate)
    {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.topic = topic;
        this.content = content;
        this.departureDate = departureDate;
    }

    @Override
    public String toString()
    {
        return "Letter{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", departureDate=" + departureDate +
                '}';
    }
}

