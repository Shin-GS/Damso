export function fetchWithCredentials(url, options = {}) {
    // Ensure options is an object and apply default settings
    const defaultOptions = {
        credentials: "include",
        method: 'GET', // Default method is GET
    };

    // Get auth and refresh tokens from localStorage
    const auth = localStorage.getItem('auth');
    const refresh = localStorage.getItem('refresh');

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

    // Function to refresh token
    const refreshToken = () => {
        return fetch('/api/auth/refresh', {
            credentials: 'include',
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({refresh}),
        })
            .then((response) => {
                if (!response.ok) {
                    return response.json().then(error => {
                        return Promise.reject(new Error("Failed to refresh token"));
                    });
                }

                return response.json();
            })
            .then((data) => {
                // auth token update
                localStorage.setItem('auth', data.result.auth);
                return data.result.auth;
            })
            .catch((error) => {
                console.error("Refresh token invalid: ", error);
                localStorage.removeItem('auth');
                localStorage.removeItem('refresh');
                throw error;
            });
    };

    // Return fetch call with then/catch for promise handling
    return fetch(url, mergedOptions)
        .then((response) => {
            // Remove invalid token if Clear-Token header is true
            if (response.headers.get('refresh-Token') === 'true') {
                if (refresh) {
                    // Attempt to refresh the token
                    return refreshToken().then((newAuth) => {
                        // Retry the original request with the new token
                        mergedOptions.headers['Authorization'] = `Bearer ${newAuth}`;
                        return fetch(url, mergedOptions);
                    });
                }
            }

            if (response.headers.get('Clear-Token') === 'true') {
                console.warn("Invalid token detected. Removing token from local storage.");
                localStorage.removeItem('auth');
            }

            // if (!response.ok) {
            //     console.log(response.json().error)
            //
            //     // Throw error for non-2xx status codes
            //     return Promise.reject(new Error(response.json().message));
            // }

            // Return the original response if no Clear-Token or successful retry
            return response;
        })
        .catch((error) => {
            // Catch and handle any network or other errors
            console.error("Fetch error:", error);
            throw error;
        });
}
