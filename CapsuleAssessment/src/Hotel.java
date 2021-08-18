public class Hotel {
    private Guest[] guests;
    private int size;

    public Hotel(int size) {
        this.guests = new Guest[size];
        this.size = size;
    }

    public int getSize() { return size; }

    public Guest[] getGuests() {
        return guests;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
