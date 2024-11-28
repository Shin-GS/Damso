export function handleErrorResponse(response) {
    if(response.ok) {
        return response;
    }

    if (response.status >= 400 && response.status < 500) {
        console.error("Client Error:", response.status, response.code, response.error);
        alert(`Client error occurred: ${response.message}`);

    }

    else if (response.status >= 500) {
        console.error("Server Error:", response.status, response.code, response.error);
        alert(`Server error occurred: ${response.message}`);
    }

    throw new Error(`Other Error: ${response.status}`);
}
