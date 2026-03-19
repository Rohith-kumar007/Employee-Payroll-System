// Render Shared Sidebar based on Role
function renderSidebar() {
    const role = localStorage.getItem('role');
    const email = localStorage.getItem('email');
    
    if (!role) {
        window.location.href = 'index.html';
        return;
    }

    const currentPage = window.location.pathname.split("/").pop();

    const sidebarHTML = `
        <div class="sidebar">
            <div class="sidebar-header">
                <span class="icon">📊</span> HR System
            </div>
            <div style="margin-bottom: 1rem; font-size: 0.8rem; color: #94a3b8;">
                Logged in as: <br/><b>${email}</b> (${role})
            </div>
            <ul class="nav-links">
                <li><a href="dashboard.html" class="${currentPage === 'dashboard.html' ? 'active' : ''}">Dashboard</a></li>
                ${role === 'ADMIN' ? `
                    <li><a href="employees.html" class="${currentPage === 'employees.html' ? 'active' : ''}">Employees</a></li>
                    <li><a href="attendance.html" class="${currentPage === 'attendance.html' ? 'active' : ''}">Attendance</a></li>
                    <li><a href="leave.html" class="${currentPage === 'leave.html' ? 'active' : ''}">Leaves</a></li>
                    <li><a href="payroll.html" class="${currentPage === 'payroll.html' ? 'active' : ''}">Payroll</a></li>
                ` : `
                    <li><a href="attendance.html" class="${currentPage === 'attendance.html' ? 'active' : ''}">Mark Attendance</a></li>
                    <li><a href="leave.html" class="${currentPage === 'leave.html' ? 'active' : ''}">My Leaves</a></li>
                    <li><a href="payroll.html" class="${currentPage === 'payroll.html' ? 'active' : ''}">My Payroll</a></li>
                `}
            </ul>
            <button class="logout-btn" onclick="logout()">Logout</button>
        </div>
    `;

    document.write(sidebarHTML);
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}
