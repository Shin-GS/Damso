export function handleErrorResponse(response) {
    if(response.ok) {
        return response;
    }

    if (response.status >= 400 && response.status < 500) {
        console.error("Client Error:", response.status, response.code, response.error);
        alert(`${response.message}`);

    }

    else if (response.status >= 500) {
        console.error("Server Error:", response.status, response.code, response.error);
        alert(`Server error: ${response.message}`);
    }

    throw new Error(`Other Error: ${response.status}`);
}
