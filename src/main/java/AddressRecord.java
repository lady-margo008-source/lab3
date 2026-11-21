import java.util.Objects;
public class AddressRecord {
    private final String city;
    private final String street;
    private final String house;
    private final Integer floor;
    public AddressRecord(String city, String street, String house, Integer floor){
        this.city=city;
        this.street=street;
        this.house=house;
        this.floor=floor;
    }
    public String getCity() {
        return city;
    }

    public int getFloor() {
        return floor;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressRecord that = (AddressRecord) o;
        return floor == that.floor &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, house, floor);
    }

    @Override
    public String toString() {
        return String.format("г. %s, %s, д. %s (этажей: %d)", city, street, house, floor);
    }
}
