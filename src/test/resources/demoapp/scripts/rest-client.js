class RestClient {
    constructor(baseUrl, baseHeaders) {
        this.baseUrl = baseUrl;
        this.baseHeaders = baseHeaders;
    }

    async get(endPoint, headers) {
        let url = this.baseUrl + endPoint;
        headers = this.getHeaders(headers);
        return await this.send(url, "GET", headers)
    }

    async post(endPoint, body, headers) {
        let url = this.baseUrl + endPoint;
        headers = this.getHeaders(headers);
        return await this.send(url, "POST", headers, body)
    }

    async put(endPoint, body, headers) {
        let url = this.baseUrl + endPoint;
        headers = this.getHeaders(headers);
        return await this.send(url, "PUT", headers, body)
    }

    async delete(endPoint, headers) {
        let url = this.baseUrl + endPoint;
        headers = this.getHeaders(headers);
        return await this.send(url, "DELETE", headers)
    }

    send(url, method, headers, body) {
        if(typeof body === "object")
            body = JSON.stringify(body);

        return new Promise((resolve, reject) => {
            fetch(url, {
                method: method,
                headers: headers,
                body: body
            })
            .then(response => resolve(response))
            .catch(error => reject(error));
        });
    }

    getHeaders(headers) {
        if(headers) 
            return Object.assign(this.headers, headers);

        else if(this.baseHeaders) 
            return this.baseHeaders;

        else 
            return {};
    }
}