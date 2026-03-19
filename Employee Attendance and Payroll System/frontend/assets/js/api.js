const BASE_URL = 'http://localhost:8081/api';

// Helper for Fetch
async function apiRequest(endpoint, method = 'GET', body = null) {
    const token = localStorage.getItem('token');
    const headers = { 'Content-Type': 'application/json' };
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = { method, headers };
    if (body) { config.body = JSON.stringify(body); }

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, config);
        
        if (response.status === 403 || response.status === 401) {
            // Unauthorized or token expired
            localStorage.clear();
            window.location.href = 'index.html';
            return;
        }

        if (!response.ok) {
            let errorMessage = 'Something went wrong';
            const textError = await response.text(); // Read stream ONCE
            
            try {
                const error = JSON.parse(textError); // Parse manually
                console.log('Full Backend Error JSON:', error);
                errorMessage = error.message || error.error || textError;
            } catch (e) {
                errorMessage = textError || `Error: ${response.status} ${response.statusText}`;
            }
            throw new Error(errorMessage);
        }

        if (response.status === 204) return null; // No content

        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Auth API
const AuthAPI = {
    login: (credentials) => apiRequest('/auth/login', 'POST', credentials),
    getProfile: () => apiRequest('/employee/profile')
};

// Employee API (Admin)
const AdminAPI = {
    getEmployees: () => apiRequest('/admin/employees'),
    createEmployee: (data) => apiRequest('/admin/employees', 'POST', data),
    updateEmployee: (id, data) => apiRequest(`/admin/employees/${id}`, 'PUT', data),
    deleteEmployee: (id) => apiRequest(`/admin/employees/${id}`, 'DELETE'),
    
    getAttendance: () => apiRequest('/admin/attendance'),
    getLeaves: () => apiRequest('/admin/leaves'),
    updateLeaveStatus: (id, status) => apiRequest(`/admin/leaves/${id}/status`, 'PUT', { status }),
    
    generatePayroll: (data) => apiRequest('/admin/payroll/generate', 'POST', data),
    getPayrolls: () => apiRequest('/admin/payroll')
};

// Employee Self Service API
const EmployeeAPI = {
    getAttendance: () => apiRequest('/employee/attendance'),
    markAttendance: (data) => apiRequest('/employee/attendance', 'POST', data),
    getLeaves: () => apiRequest('/employee/leaves'),
    applyLeave: (data) => apiRequest('/employee/leaves', 'POST', data),
    getPayroll: () => apiRequest('/employee/payroll')
};
