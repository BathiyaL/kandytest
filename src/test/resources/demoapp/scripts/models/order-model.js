class OrderModel {
    /**
     * 
     * @param {number} id 
     * @param {number} petId 
     * @param {number} quantity 
     * @param {string} shipDate 
     * @param {string} status valid choices -> "placed", "approved", "delivered"
     * @param {boolean} complete 
     */
    constructor(id, petId, quantity, shipDate, status, complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
}