/**
 * GLOBAL
 */
let logger;



/**
 * MAIN
 */
async function main() {
    /**
     * UI
     */
    let sendRequestBtn = document.getElementById("send-requests");
    let logConsoleEle = document.getElementById("log-console");


    /**
     * Setting up
     */
    logger = new Logger(logConsoleEle);


    /**
     * STARTUP
     */
    sendRequestBtn.addEventListener('click', async () => {
        console.log("lakdfjldskjflskdjlk")

        let petStore = new PetStore();
        let order = new Order();

        await petStore.sendRequestCallback();
        await order.sendRequestCallback();
        
    }, false);
}


class PetStore {
    constructor() {
        this.petStoreHost = "https://petstore.swagger.io/v2";

        this.restClient = new RestClient(this.petStoreHost, {
            "Content-Type": "application/json",
            "Accept": "application/json"
        });
    }

    async sendRequestCallback(event) {
        try {
            let resAddPet = await this.addPet(304, "sample", "available");

            let resGetPet = await this.getPet(304);

            let resUpdatePet = await this.updatePet(304, "sample2", "sold");

            let resDelPet = await this.deletePet(304)

        } catch(error) {
            logger.error("ERROR: " + error.message);
        }
    }
    
    async addPet(id, name, status) {
        let pet = new PetModel(id, name, status);
        let endPoint = "/pet";

        let response = await this.restClient.post(endPoint, pet)

        if(response.status !== 200)
            throw new Error("Response status is: " + response.status);

        logger.log("PASS:  adding pet successful: " + response.status)

        return response;
    }

    async getPet(id) {
        let endPoint = `/pet/${id}`;

        let response = await this.restClient.get(endPoint);

        if(response.status !== 200)
            throw new Error("Response status is: " + response.status);

        logger.log("PASS:  getting pet successful: " +  response.status)

        return response;
    }

    async updatePet(id, newName, newStatus) {
        let endPoint = "/pet"
        let pet = new PetStore(id, newName, newStatus);

        let response = await this.restClient.put(endPoint, pet);

        if(response.status !== 200)
            throw new Error("Response status is: " + response.status)

        logger.log("PASS:  updating pet successful: " +  response.status)

        return response;
    }

    async deletePet(id) {
        let endPoint = `/pet/${id}`;

        let response = await this.restClient.delete(endPoint);

        if(response.status !== 200)
            throw new Error("Response status is: " + response.status)
        
        logger.log("PASS:  deleting pet successful: " + response.status)

        return response;
    }

    

}

class Order {

    constructor() {
        this.petStoreHost = "https://petstore.swagger.io/v2";

        this.restClient = new RestClient(this.petStoreHost, {
            "Content-Type": "application/json",
            "Accept": "application/json"
        });
    }

    async sendRequestCallback(event) {
        try {

            // store inventory
            let storeInvRes = await this.getStoreInventory();
            logger.log("PASS:  getting store inventory successful: " + storeInvRes.status)

            // place order
            let placeOrderRes = await this.placeOrder(
                290,
                304,
                1,
                "2019-01-24T04:26:39.671Z",
                "placed",
                false
            );
            logger.log("PASS: placing order successful: " + placeOrderRes.status)

        } catch(error) {
            logger.error("ERROR: " + error.message);
        }
    }

    async getStoreInventory() {
        let endPoint = "/store/inventory";

        let response = await this.restClient.get(endPoint);

        if (response.status !== 200)
            throw new Error("Response status is: " + response.status)

        return response;
    }

    async placeOrder(id, petId, quantity, shipDate, status, complete) {
        let order = new OrderModel(id, petId, quantity, shipDate, status, complete);
        let endPoint = "/store/order";

        let response = await this.restClient.post(endPoint, order);

        if (response.status !== 200)
            throw new Error("Response status is: " + response.status)

        return response;
    }
}

class User {
    async sendRequestCallback(event) {
        try {

            let addUserRes = await this.addUser(
                 12,
                "usrname",
                "fname",
                "lname",
                "gmail",
                "passwd",
                1239286,
                0
            );

            logger.log("PASS: creating user successful: " + addUserRes.status)

        } catch(error) {
            logger.error("ERROR: " + error.message);
        }
    }
    async addUser(id, username, firstName, lastName, email, password, phone, userStatus) {
        let user = new UserModel (
            id,
            username,
            firstName,
            lastName,
            email,
            password,
            phone,
            userStatus
        );
        let endPoint = "/user";

        let response = this.restClient.post(endPoint, user);

        if(response.status !== 200)
            throw new Error("Response status is: " + response.status)

        return response;
    }
}

class Logger {
    
    /**
     * 
     * @param {HTMLElement} logConsoleContainerElement 
     */
    constructor(logConsoleContainerElement) {
        this.logConsole = logConsoleContainerElement;
    }

    log(message) {
        let ele = document.createElement('div');

        ele.style.color = "gray";
        ele.innerHTML = message;

        this.logConsole.appendChild(ele);
    }

    error(message) {
        let ele = document.createElement('div');

        ele.style.color = "red";
        ele.innerHTML = message;

        this.logConsole.appendChild(ele);
        console.error(error);
    }
}

main();