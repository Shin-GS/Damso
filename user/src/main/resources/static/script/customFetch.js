export function fetchWithCredentials(url, options = {}) {
    // Ensure options is an object and apply default settings
    const defaultOptions = {
        credentials: "include",
        method: 'GET', // Default method is GET
    };

    // Get auth from localStorage
    const auth = localStorage.getItem('auth');

    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...options.headers, // Merge custom headers
            ...(auth ? {'Authorization': `Bearer ${auth}`} : {}), // Add Authorization header if auth exists
        },
    };

    // If method is 'POST', 'PUT', or 'PATCH', ensure body is included (for JSON payload)
    if (['POST', 'PUT', 'PATCH'].includes(mergedOptions.method) && options.body) {
        mergedOptions.body = JSON.stringify(options.body); // Convert body to JSON string
    }

    // Return fetch call with then/catch for promise handling
    return fetch(url, mergedOptions)
        .then((response) => {
            if (!response.ok) {
                // Throw error for non-2xx status codes
                return Promise.reject(new Error(`HTTP error! Status: ${response.status}`));
            }
            // Parse JSON response if applicable
            return response;
        })
        .catch((error) => {
            // Catch and handle any network or other errors
            console.error("Fetch error:", error);
            throw error;
        });
}
