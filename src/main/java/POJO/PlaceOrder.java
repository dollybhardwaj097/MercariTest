package POJO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PlaceOrder {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceOrder that = (PlaceOrder) o;
        return id == that.id && petId == that.petId && quantity == that.quantity && complete == that.complete && Objects.equals(shipDate, that.shipDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petId, quantity, shipDate, status, complete);
    }

    public static class Builder {
        private long id;
        private long petId;
        private int quantity;
        private String shipDate;
        private String status;
        private boolean complete;

        public Builder() {

        }


        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withPetId(long petId) {
            this.petId = petId;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withShipDate(String shipDate) {
            this.shipDate = shipDate;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withComplete(boolean complete) {
            this.complete = complete;
            return this;
        }

        public PlaceOrder build() {
            PlaceOrder request = new PlaceOrder();
            request.id = this.id;
            request.petId = this.petId;
            request.quantity = this.quantity;
            request.shipDate = this.shipDate;
            request.status = this.status;
            request.complete = this.complete;
            return request;
        }
    }
}
