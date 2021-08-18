public class Guest {
    private String name;
    private int room;

    public Guest(String name) {
        this.name = name;
        this.room = -1;
    }

    public String getName() {
        return name;
    }

    public int getRoom() {
        return room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
